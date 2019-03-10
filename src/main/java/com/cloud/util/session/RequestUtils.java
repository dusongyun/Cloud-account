package com.cloud.util.session;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author crc
 * @time 2015-5-29 下午5:07:30
 * @description <pre>
 * 
 * </pre>
 *
 */
public class RequestUtils {
	private static final Logger log = LoggerFactory
			.getLogger(RequestUtils.class);

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParams(HttpServletRequest request) {
		Map<String, String[]> map;
		if (request.getMethod().equalsIgnoreCase("POST")) {
			map = request.getParameterMap();
		} else {
			String s = request.getQueryString();
			if (StringUtils.isBlank(s)) {
				return new HashMap<String, Object>();
			}
			try {
				s = URLDecoder.decode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("encoding UTF-8 not support?", e);
			}
			map = parseQueryString(s);
		}

		Map<String, Object> params = new HashMap<String, Object>(map.size());
		int len;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			len = entry.getValue().length;
			if (len == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else if (len > 1) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}

	public static Map<String, String[]> parseQueryString(String s) {
		String valArray[] = null;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++) {
					valArray[i] = oldVals[i];
				}
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	public static Map<String, String> getRequestMap(HttpServletRequest request,
			String prefix) {
		return getRequestMap(request, prefix, false);
	}

	public static Map<String, String> getRequestMapWithPrefix(
			HttpServletRequest request, String prefix) {
		return getRequestMap(request, prefix, true);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getRequestMap(
			HttpServletRequest request, String prefix, boolean nameWithPrefix) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		String name, key, value;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.startsWith(prefix)) {
				key = nameWithPrefix ? name : name.substring(prefix.length());
				value = StringUtils.join(request.getParameterValues(name), ',');
				map.put(key, value);
			}
		}
		return map;
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	public static String getUserIp(HttpServletRequest request) {
		// 获取客户端前台IP进行解析
		String UserIp = request.getHeader("x-forwarded-for");
		if (UserIp == null || UserIp.length() == 0
				|| "unknown".equalsIgnoreCase(UserIp)) {
			UserIp = request.getHeader("Proxy-Client-IP");
		}
		if (UserIp == null || UserIp.length() == 0
				|| "unknown".equalsIgnoreCase(UserIp)) {
			UserIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (UserIp == null || UserIp.length() == 0
				|| "unknown".equalsIgnoreCase(UserIp)) {
			UserIp = request.getRemoteAddr();
		}
		return UserIp;
	}

	/**
	 * 
	 * @description <pre>
	 * 判断是否为ajax请求
	 * </pre>
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		if (request != null) {
			if ("XMLHttpRequest".equalsIgnoreCase(request
					.getHeader("X-Requested-With"))
					|| (request.getHeader("Content-Type") != null && request
							.getHeader("Content-Type").indexOf(
									"application/json") > -1)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSearchParams(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String[]> map = request.getParameterMap();
		if (null != map && !map.isEmpty()) {
			Object[] value;
			String key;
			String sValue;
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				key = (String) entry.getKey();
				// 规定页面所有查询的字段都是以query_开头，如果不是,则过滤
				if (key.indexOf("query_") != 0) {
					continue;
				}
				value = (Object[]) entry.getValue();
				if (ArrayUtils.isNotEmpty(value)) {
					if (value[0] instanceof String) {
						sValue = StringUtils.trimToEmpty((String) value[0]);
						if (StringUtils.isNotEmpty(sValue)) {
							resultMap.put(key, sValue);
						}
					} else {
						resultMap.put(key, value[0]);
					}
				}
			}
		}
		setAttribute(resultMap, request);
		return resultMap;
	}

	private static void setAttribute(Map<String, Object> params,
			HttpServletRequest request) {
		if (null != params) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}
	}
}
