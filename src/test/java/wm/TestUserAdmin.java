package wm;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserAdminVo;
import com.cloud.util.encode.MD5Util;
import com.cloud.util.http.HttpClientUtil;
import com.cloud.util.http.HttpClientUtil.HttpClientResult;

public class TestUserAdmin {
	public static void main(String args[]) {
//		new TestUserAdmin().add();
//		new TestUserAdmin().checkUsername();
//		new TestUserAdmin().sendSmscode()	;
//		new TestUserAdmin().login();
		new TestUserAdmin().getUserInfo();
	}
		public void login() {
			LoginVo param = new LoginVo();
			param.setUsername("wm");
            param.setPassword(MD5Util.encryptPWD("123456"));
			param.setSmscode("562672");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-admin/login.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void sendSmscode() {
			LoginVo param = new LoginVo();
			param.setUsername("wm");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-admin/sendSmscode.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void checkUsername() {
			LoginVo param = new LoginVo();
			param.setUsername("wm");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-admin/checkUsername.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void add() {
			UserAdminVo param = new UserAdminVo();
			param.setUsername("wm");
			param.setPassword(MD5Util.encryptPWD("123456"));
			param.setMobile("18017373806");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-admin/add.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
		public void getUserInfo() {
			UserAdminVo param = new UserAdminVo();
			param.setId("A20190307212109535075");
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("info", JSON.toJSONString(param));
	
			HttpClientResult res = HttpClientUtil.httpClientOfPost(
					"http://localhost:8080/cloud-account/user-admin/getUserInfo.do", parameters);
			System.out.println("返回结果：" + res.getValue());
		}
}
