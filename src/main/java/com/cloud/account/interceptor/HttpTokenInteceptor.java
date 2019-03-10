package com.cloud.account.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cloud.util.StringUtils;
import com.cloud.util.auth.annotation.HttpToken;
import com.cloud.util.http.result.BaseResp;

/**
 * @描述: 用户请求重复过滤
 * @作者: dusai
 * @作成时间: 2018年4月19日 下午3:43:41
 */
public class HttpTokenInteceptor extends HandlerInterceptorAdapter {

    public static final String UTF_8 = "UTF-8";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("HttpTokenInteceptor Action");

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod methodHandler = (HandlerMethod) handler;
        HttpToken httpToken = methodHandler.getMethodAnnotation(HttpToken.class);
        // 如果方法中添加了@HttpToken,进行权限值
        if (httpToken != null) {
            String openId = getOpenId(request);

            // 拦截器本身是单例的，为了防止高并发，session数据需要同步化处理
            synchronized (this) {
                //String uri = request.getRequestURI().replaceAll("/", "");
                String uri = request.getParameter("info");
                if (request.getSession().getAttribute(openId + "_" + uri) == null) {
                    request.getSession().setAttribute(openId + "_" + uri, System.currentTimeMillis());
                } else {
                    long preMillis = (long) request.getSession().getAttribute(openId + "_" + uri);
                    float seconds = (System.currentTimeMillis() - preMillis) / 1000F;
                    if (seconds / 1000 < 15F) {

                        System.out.println("自动过滤用户重复请求[15S以内],直接返回结果");
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json; charset=utf-8");
                        PrintWriter out = null;
                        try {
                            BaseResp result = (BaseResp) request.getSession().getAttribute(
                                    openId + "_" + uri + "_result");
                            JSONObject res = new JSONObject();
                            res.put("code", "0000");
                            res.put("msg", "自动过滤用户重复请求[15S以内],直接返回结果");
                            res.put("success", "true");
                            res.put("data", result.getData());
                            request.getSession().removeAttribute(openId + "_" + uri);
                            request.getSession().removeAttribute(openId + "_" + uri + "_result");
                            out = response.getWriter();
                            out.append(res.toString());
                            return false;
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.sendError(500);
                            return false;
                        }
                    }
                }
            }
        } else {
            return true;
        }

        return true;
    }

    /**
     * @描述：验证用户是否登录 true:通过 false:不通过
     * @作者：dusai
     * @日期：下午2:02:09
     */

    /**
     * @描述: 从用户请求參數或者cookie中得到ut
     * @作者: dusai
     * @时间: 2018年4月19日 下午3:42:14
     */
    public static String getOpenId(HttpServletRequest req) {

        // 1,从请求参数中获取
        String openId = req.getParameter("openId");

        if (StringUtils.isNotBlank(openId)) {
            openId = openId.trim();
            openId = openId.toLowerCase();
            openId = openId.replaceAll(" ", "");
            openId = openId.replaceAll("\t", "");
            openId = openId.replaceAll("\r", "");
            openId = openId.replaceAll("\n", "");
            return openId;
        }

        // 2,从cookie中获取
        Cookie[] cookies = req.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (int i = 0; i < cookies.length; i++) {
                if ("openId".equalsIgnoreCase(cookies[i].getName())) {
                    openId = cookies[i].getValue();
                    return openId;
                }
            }
        }

        // 3,从header中获取
        if (StringUtils.isBlank(openId)) {
            openId = req.getHeader("openId");
            return openId;
        }

        return openId;
    }

}
