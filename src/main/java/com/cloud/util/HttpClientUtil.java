package com.cloud.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class HttpClientUtil {
	private static final Logger log = Logger.getLogger(HttpClientUtil.class);

	enum EnumHttpClientResultCode {
		SUCCESS, // 成功
		FAILED, // 失败
		TIMEOUT// 超时
	}

	public static class HttpClientResult {
		private EnumHttpClientResultCode code;
		private String value;
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isSuccess() {
			return EnumHttpClientResultCode.SUCCESS == this.code;
		}

		public boolean isTimeout() {
			return EnumHttpClientResultCode.TIMEOUT == this.code;
		}

		public boolean isFailed() {
			return EnumHttpClientResultCode.FAILED == this.code;
		}

		public EnumHttpClientResultCode getCode() {
			return code;
		}

		public void setCode(EnumHttpClientResultCode code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * 调用接口失败返回对象
		 * 
		 * @param msg
		 * @return
		 */
		public static HttpClientResult defaultFail() {
			HttpClientResult resp = new HttpClientResult();
			resp.setCode(EnumHttpClientResultCode.FAILED);
			resp.setMessage("默认调用接口失败！");
			return resp;
		}

		/**
		 * 调用接口成功返回对象
		 * 
		 * @param msg
		 * @return
		 */
		public static HttpClientResult defaultSuccess() {
			HttpClientResult resp = new HttpClientResult();
			resp.setCode(EnumHttpClientResultCode.SUCCESS);
			resp.setMessage("默认调用接口成功！");
			return resp;
		}

		
	}

	/**
	 * HTTP POST的请求
	 * @param url
	 * @param params
	 * @return HttpClientResult
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HttpClientResult httpClientOfPost(String url, Map params) {
		HttpClientResult result = new HttpClientResult();

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				formparams.add(new BasicNameValuePair(key, ObjectUtil
						.defaultIfNull(params.get(key), "").toString()));
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					"UTF-8");
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).
					setConnectTimeout(2000).build();//设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			httppost.setEntity(entity);
			CloseableHttpClient hclient = HttpClients.createDefault();
			HttpResponse response = hclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			String rspVal = EntityUtils.toString(httpEntity,"UTF-8");
			result.setValue(rspVal);
			result.setCode(EnumHttpClientResultCode.SUCCESS);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.setCode(EnumHttpClientResultCode.FAILED);
			result.setMessage(e.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setCode(EnumHttpClientResultCode.FAILED);
			result.setMessage(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			result.setCode(EnumHttpClientResultCode.TIMEOUT);
			result.setMessage(e.toString());
		}
		return result;
	}
	
	/**
	 * HTTP GET请求
	 * @param url
	 * @param params
	 * @return HttpClientResult
	 */
	public static HttpClientResult httpClientOfGet(String url, String param) {
		HttpClientResult result = new HttpClientResult();
		try {
			log.info("Create HttpGet:" + url+param);
			HttpGet httpget = new HttpGet(url+param);
			CloseableHttpClient hclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).
					setConnectTimeout(2000).build();//设置请求和传输超时时间
			httpget.setConfig(requestConfig);
			HttpResponse response = hclient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();
			String rspVal = EntityUtils.toString(httpEntity,"UTF-8");
			result.setValue(rspVal);
			result.setCode(EnumHttpClientResultCode.SUCCESS);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.setCode(EnumHttpClientResultCode.FAILED);
			result.setMessage(e.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.setCode(EnumHttpClientResultCode.FAILED);
			result.setMessage(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			result.setCode(EnumHttpClientResultCode.TIMEOUT);
			result.setMessage(e.toString());
		}
		return result;
	}
	
	
	
	

}
