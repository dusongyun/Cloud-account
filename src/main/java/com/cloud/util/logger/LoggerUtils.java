package com.cloud.util.logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
/**
 * 获取请求的ip
 * @author 作者：wangjian  Email：202109618@qq.com
 * @version 创建时间：2018年5月8日 下午2:16:35
 */
public class LoggerUtils {
	
	 	public static final String LOGGER_RETURN = "_logger_return";

	    private LoggerUtils() {}

	    /**
	     * 获取客户端ip地址
	     * @param request
	     * @return
	     */
	    public static String getIpAddr(HttpServletRequest request)
	    {
	    	
	        String ip = request.getHeader("x-forwarded-for");
	        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getRemoteAddr();
	        }

	        // 多个路由时，取第一个非unknown的ip
	        final String[] arr = ip.split(",");
	        for (final String str : arr) {
	            if (!"unknown".equalsIgnoreCase(str)) {
	                ip = str;
	                break;
	            }
	        }
	        return ip;
	    }

	    /**
	     * 判断是否为ajax请求
	     * @param request
	     * @return
	     */
	    public static String getRequestType(HttpServletRequest request) {
	        return request.getHeader("X-Requested-With");
	    }
	    
		// 建议使用
		/**
		 * 得到请求的ip地址
		 * 
		 * @param request
		 * @return
		 */
		public static String getCliectIp(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Real-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
				if (ip.indexOf(",") > 0) {
					ip = ip.substring(0, ip.indexOf(","));
				}
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = getIpAddr(request);
			}
			return ip;
		}
}
