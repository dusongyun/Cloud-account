package com.cloud.util.http;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;



/**
 * 
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

	enum EnumHttpClientResultCode {
		SUCCESS, // 成功
		FAILED, // 失败
		TIMEOUT
		// 超时
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

	}

	/**
	 * POST的请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @author zzy
	 */
	public static HttpClientResult httpClientOfPost(String url, Map params) {
		System.out.println("url---->>"+url);
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
//			httppost.setHeader("ut", "");
			httppost.setHeader("openId", "wx190716");//线上
//			httppost.setHeader("openId", "oAFu31RMhIXfepVBqizNBCupmTXo");//测试
			
//			httppost.setHeader("openId", "kefu");
			
//			httppost.setHeader("Cookie", "jsluid=a24913af67d5180974c18523e24d08a0; JSESSIONID=YKxXWw1fRJCVSClWvv8brv2ZZrJsBVLgpQCjDxGnJHNbvSMKn021!-618727874");
			httppost.setEntity(entity);
			HttpClient hclient = new DefaultHttpClient();  
			hclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,3000);//连接时间
			hclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,3000);//数据传输时间
			HttpResponse response = hclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			String rspVal = EntityUtils.toString(httpEntity);
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
	 * get的请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @author zzy
	 */
	public static HttpClientResult httpClientOfGet(String url, String param) {
		HttpClientResult result = new HttpClientResult();
		try {
			HttpGet httpget = new HttpGet(url + param);
			@SuppressWarnings("resource")
            HttpClient hclient = new DefaultHttpClient();
//			hclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,6000);//连接时间
//			hclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);//数据传输时间
			HttpResponse response = hclient.execute(httpget);
			int status = response.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity httpEntity = response.getEntity();
				String rspVal = EntityUtils.toString(httpEntity);
				result.setValue(rspVal);
				result.setCode(EnumHttpClientResultCode.SUCCESS);
			}else{
				result.setCode(EnumHttpClientResultCode.FAILED);
			}
			
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
	 * get的请�?
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @author zzy
	 */
	public static HttpClientResult httpsOfGet(String url, String param) {
		HttpClientResult result = new HttpClientResult();
		try {
			HttpGet httpget = new HttpGet(url + param);
			HttpClient hclient = new SSLClient();
			hclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,6000);//连接时间
			hclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);//数据传输时间
			HttpResponse response = hclient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();
			String rspVal = EntityUtils.toString(httpEntity,"utf-8");
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static final Map<String, Object> toMap(Object bean)  
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> returnMap = new HashMap<String, Object>();  
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
        for (int i = 0; i< propertyDescriptors.length; i++) {  
            PropertyDescriptor descriptor = propertyDescriptors[i];  
            String propertyName = descriptor.getName();  
            if (!propertyName.equals("class")) {  
                Method readMethod = descriptor.getReadMethod();  
                Object result = readMethod.invoke(bean, new Object[0]);  
                if (result != null) {  
                    returnMap.put(propertyName, result);  
                } else {  
                    returnMap.put(propertyName, "");  
                }  
            }  
        }  
        return returnMap;  
    } 
}
