package com.cloud.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.cloud.util.MyX509TrustManager;

public class HttpsClient {

	private static CloseableHttpClient buildSSLCloseableHttpClient()
			throws Exception {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
				new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();
		// ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

	/**
	 * 发送 SSL POST请（HTTPS），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @author Charlie.chen
	 */
	public static String doPostSSL(String url, Map<String, Object> params) {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			CloseableHttpClient buildSSLCloseableHttpClient = buildSSLCloseableHttpClient();
			System.setProperty("jsse.enableSNIExtension", "false");
			// httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(
					params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(),
						entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset
					.forName("utf-8")));
			response = buildSSLCloseableHttpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			System.out.println("正在发送请求");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// System.out.println("接受到请求了！！");
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			// System.out.println("-----------");
			// System.out.println(buffer.toString());
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}
	public static void main(String[] args) {
		try {
			CloseableHttpClient buildSSLCloseableHttpClient = buildSSLCloseableHttpClient();
			System.setProperty("jsse.enableSNIExtension", "false");
			HttpGet httpGet = new HttpGet(
					"https://180.153.50.169:9998/backRedis/backWhiteList");
			HttpResponse response = buildSSLCloseableHttpClient
					.execute(httpGet);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String content = sb.toString();
			System.out.println(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
