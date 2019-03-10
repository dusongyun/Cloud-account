package wm;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserCompanyVo;
import com.cloud.util.encode.MD5Util;
import com.cloud.util.http.HttpClientUtil;
import com.cloud.util.http.HttpClientUtil.HttpClientResult;

public class TestUserCompany {
	public static void main(String args[]) {
//		new TestUserCompany().add();
//		new TestUserCompany().checkUsername();
//		new TestUserCompany().sendSmscode()	;
//		new TestUserCompany().login();
//		new TestUserCompany().firstlogin();
//		new TestUserCompany().getUserInfo();
		new TestUserCompany().resetPassword();
	}
	
	public void firstlogin() {
		LoginVo param = new LoginVo();
		param.setUsername("hxy");
        param.setPassword(MD5Util.encryptPWD("123456"));
		param.setSmscode("397732");
		param.setRegistercode("81705951");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("info", JSON.toJSONString(param));

		HttpClientResult res = HttpClientUtil.httpClientOfPost(
				"http://localhost:8080/cloud-account/user-company/firstlogin.do", parameters);
		System.out.println("返回结果：" + res.getValue());
	}
		public void login() {
			LoginVo param = new LoginVo();
			param.setUsername("hxy");
            param.setPassword(MD5Util.encryptPWD("123456"));
			param.setSmscode("397732");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-company/login.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void sendSmscode() {
			LoginVo param = new LoginVo();
			param.setUsername("hxy");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-company/sendSmscode.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void checkUsername() {
			LoginVo param = new LoginVo();
			param.setUsername("hxy1");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-company/checkUsername.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void add() {
			UserCompanyVo param = new UserCompanyVo();
			param.setCompanyCode("hxy");
			param.setCompanyName("XXX公司");
			param.setCorporationIdno("320322198807024747");
			param.setCorporationName("黄小云");
			param.setCorporationPhone("18017373807");
			param.setCorporationPic1("http://baidu1.com");
			param.setCorporationPic2("http://baidu2.com");
			param.setCorporationPic3("http://baidu3.com");
			param.setManagerIdno("320322198807024748");
			param.setManagerName("黄豆豆");
			param.setManagerPhone("18017373806");
			param.setManagerPic1("http://baidu4.com");
			param.setManagerPic2("http://baidu5.com");
			param.setManagerPic3("http://baidu6.com");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-company/add.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void getUserInfo() {
			UserCompanyVo param = new UserCompanyVo();
			param.setId("C20190308142749913601");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-company/getUserInfo.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void resetPassword() {
			LoginVo param = new LoginVo();
			param.setId("C20190308142749913601");
			param.setPassword(MD5Util.encryptPWD("123456"));
			param.setSmscode("397732");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-company/resetPassword.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
}
