package com.cloud.account.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.util.IPAddress;
import com.cloud.util.StringUtils;
import com.cloud.util.auth.annotation.HttpToken;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.logger.ControllerLog;
import com.cloud.util.logger.LogItem;

@Aspect
@Component
public class LogAspect {
    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.gpai.nams.controller.*.*(..))")
    public void logPointCut() {
        System.out.println("日志切面定义....");
    }

    /**
     * 日志开启环绕通知
     */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("日志开启环绕通知....");

        LogItem log = new LogItem();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String url = request.getRequestURI();
        if (StringUtils.isNotBlank(url)) {
            log.setRequesttime(new Date().toString());
            log.setRequestopenid(request.getHeader("openId"));
            String info = request.getParameter("info");
            log.setRequestinfo(info);
            log.setRequesttype(url);
            log.setRequestip(IPAddress.getIpAddr(request));
            log.setUserAgent(request.getHeader("User-Agent"));
        }
        // 获取开始时间
        long startTime = System.currentTimeMillis();
        // 获取被切函数的 返回值
        Object object = pjp.proceed();
        // 获取结束时间
        long endTime = System.currentTimeMillis();
        log.setDatatime(String.valueOf(endTime - startTime));

        if (object instanceof BaseResp) {
            BaseResp result = (BaseResp) object;
            log.setResponsecode(result.getCode());
            log.setResponsemsg(result.getMsg());
            // 需要HTTP拦截的接口需要存储返回值结果
            String uri = request.getParameter("info");
            if (getHttpToken(pjp) != null) {
                request.getSession().setAttribute(getOpenId(request) + "_" + uri + "_result", result);
            }
        }

        ControllerLog.writeFileLogItem(log);

        return object;
    }

    private HttpToken getHttpToken(ProceedingJoinPoint pj) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) pj.getSignature();
        Method method = joinPointObject.getMethod();

        boolean flag = method.isAnnotationPresent(HttpToken.class);
        if (flag) {
            HttpToken annotation = method.getAnnotation(HttpToken.class);
            return annotation;
        } else {
            // 如果方法上没有注解，则搜索类上是否有注解
            HttpToken classAnnotation = AnnotationUtils.findAnnotation(joinPointObject.getMethod().getDeclaringClass(),
                    HttpToken.class);
            if (classAnnotation != null) {
                return classAnnotation;
            } else {
                return null;
            }
        }
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
    public String getOpenId(HttpServletRequest req) {

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
