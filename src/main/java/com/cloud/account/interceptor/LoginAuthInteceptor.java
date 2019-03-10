package com.cloud.account.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cloud.account.entity.UserAdmin;
import com.cloud.account.service.UserAdminService;
import com.cloud.util.StringUtils;
import com.cloud.util.auth.annotation.LoginAuth;

/**
 * @描述: 用户登陆验证
 * @作者: dusai
 * @作成时间: 2018年4月19日 下午3:43:41
 */
public class LoginAuthInteceptor extends HandlerInterceptorAdapter {

    public static final String UTF_8 = "UTF-8";

    @Resource(name = "userAdminServiceImpl")
    UserAdminService userAdminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("LoginAuthInteceptor Action");

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod methodHandler = (HandlerMethod) handler;
        LoginAuth auth = methodHandler.getMethodAnnotation(LoginAuth.class);
        // 如果方法中添加了@LoginAuth,进行权限值
        if (auth != null) {

            String openId = getOpenId(request);

            // 用户ID :实时显示后台的session策略暂时取消
            if (StringUtils.isNotBlank(openId)) {
            	
            	//查询用户信息
                UserAdmin user = null;
//                		userService.selectUserByOpenId(openId);
                if (user == null) {
                    request.getRequestDispatcher("/user/loginMsg.do").forward(request, response);
                    return false;
                } else {
                    
                }
            } else {
                request.getRequestDispatcher("/user/loginMsg.do").forward(request, response);
                return false;
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
        String openId = req.getParameter("ut");

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

    /**
     * @描述：验证用户是否登录 true:通过 false:不通过
     * @作者：dusai
     * @日期：下午2:02:09
     */

    /**
     * @描述:请求类型：固定值：6EA3A4869D4D046A6AFAAB4E50A1000A5F52F5D1A0BBCB3D1A880ABB
     * @作者: dusai
     * @时间: 2018年4月19日 下午3:42:14
     */
    public static String getVisitingType(HttpServletRequest req) {

        // 1,从请求参数中获取
        String openId = req.getParameter("VisitingType");

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
                if ("VisitingType".equalsIgnoreCase(cookies[i].getName())) {
                    openId = cookies[i].getValue();
                    return openId;
                }
            }
        }

        // 3,从header中获取
        if (StringUtils.isBlank(openId)) {
            openId = req.getHeader("VisitingType");
            return openId;
        }

        return openId;
    }
}
