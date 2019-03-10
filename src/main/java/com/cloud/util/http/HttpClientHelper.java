package com.cloud.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * 
 * 
 * 项目名称：shanglv 类名称：HttpClientHelper 类描述：外面接口访问类 创建人：jianglh 创建时间：May 19, 2015
 * 6:27:54 PM 修改人：jianglh 修改时间：May 19, 2015 6:27:54 PM 修改备注：
 * 
 * @version
 * 
 */
public class HttpClientHelper {


	/*
	 * 请求超时毫秒数
	 */
	private static final int timeOut = 60000;

	private static RequestConfig config = RequestConfig.custom()
			.setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();

	/*
	 * 编码
	 */
	private static final String charset = "UTF-8";

	/*
	 * 代理信息
	 */
	private static final boolean isProxy = false;
	private static final String proxyIp = "192.168.19.9";
	private static final int proxyPort = 80;


	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */

	public static String sendPost(String url, Map<String,String> params) {
		return sendPost(url, params, null);
	}
	public static String sendPost(String url, Map<String,String> params,Map<String,String> headers) {
		String msg = null;
		HttpPost m = null;
		try {
			HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
			HttpClientBuilder builder = HttpClientBuilder.create();
			builder.setConnectionManager(connManager);
			if(isProxy){
				builder.setProxy(new HttpHost(proxyIp, proxyPort));
			}
			HttpClient client = builder.build();
			m = new HttpPost(url);
			m.setConfig(config);
			if (MapUtils.isNotEmpty(headers)) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					m.addHeader(key, headers.get(key));
				}
			}
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			if (MapUtils.isNotEmpty(params)) {
				Set<String> keys = params.keySet();
				for (String key : keys) {
					parameters.add(new BasicNameValuePair(key,params.get(key)));
				}
			}
			HttpEntity entity = new UrlEncodedFormEntity(parameters,"utf-8");
			m.setEntity(entity);
			HttpResponse response = client.execute(m);
			if (response.getStatusLine().getStatusCode() == 200) {
				msg = EntityUtils.toString(response.getEntity(), charset);
			} else {
//				logger.error("http请求失败:"
//						+ response.getStatusLine().getStatusCode());
			}
		} catch (UnsupportedEncodingException e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ClientProtocolException e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ConnectTimeoutException e){
//			logger.error("http请求超时");
		} catch (IOException e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		} finally{
			m.releaseConnection();
		}
		return msg;
	}
	
	
	 /** 
     * 发送HTTP_POST请求,json格式数据 
     * @param url 
     * @param body 
     * @return 
     * @throws Exception 
     */  
    public static String sendPostByJson(String url, String body) throws Exception {  
        CloseableHttpClient httpclient = HttpClients.custom().build();  
        HttpPost post = null;  
        String resData = null;  
        CloseableHttpResponse result = null;  
        try {  
            post = new HttpPost(url);  
            HttpEntity entity2 = new StringEntity(body, Consts.UTF_8);  
            post.setConfig(RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());  
            post.setHeader("Content-Type", "application/json");  
            post.setEntity(entity2);  
            result = httpclient.execute(post);  
            if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {  
                resData = EntityUtils.toString(result.getEntity(),"utf-8");  
            }  
        } finally {  
            if (result != null) {  
                result.close();  
            }  
            if (post != null) {  
                post.releaseConnection();  
            }  
            httpclient.close();  
        }  
        return resData;  
    }  
	/**
	 * 连续添加参数，并将参数组合成Map
	 * @param k
	 * @param v
	 * @return
	 * 例如:	<pre>
	 * HttpClientHelper.addParam("k1", "v1")	
		.addParam("k2", "v2")	
		.addParam("k3", "v3").getParamsMap();	</pre>
	 */
	public static HttpParams addParam(String k,String v){
		HttpParams p = new HttpParams();
		return p.addParam(k, v);
	}
	public static class HttpParams{
		private Map<String,String> map;
		public HttpParams addParam(String k,String v){
			if(map==null){
				map = new HashMap<>();
			}
			map.put(k,v);
			return this;
		}
		public Map<String,String> getParamsMap(){
			return map;
		}
	}
}
