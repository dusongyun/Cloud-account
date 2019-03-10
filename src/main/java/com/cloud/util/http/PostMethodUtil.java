package com.cloud.util.http;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;




/**
 * 
 */
public class PostMethodUtil {
	private static InputStream is;
	/**
	 * POST的请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @author zzy
	 */
	public static String Post(String url, String info) {
		String result = null;
		PostMethod postMethod =null;
		try {
			
			postMethod = new UTF8PostMethod(url);
			postMethod.setRequestBody(info);
			postMethod.setRequestHeader("Content-Type", "application/json");;
			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			// 301或302
			if (statusCode == HttpStatus.SC_OK) {
				// 获取返回数据
				is = postMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				StringBuffer tempString = new StringBuffer();
				String str;
				while ((str = reader.readLine()) != null) {
					tempString.append(str);
				}
				result = tempString.toString();
				}
			} catch (IOException e) {
				// 发生网络异常
				e.printStackTrace();
			} finally {
				// 释放连接
				postMethod.releaseConnection();
			}
			return result;
		}
}
