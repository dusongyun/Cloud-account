package com.cloud.account.vo;

import java.io.Serializable;

import com.cloud.util.validation.annotation.Param;

public class LoginVo implements Serializable {

	private static final long serialVersionUID = 8740063960887018514L;
	private String id;
	
	private String username;
	private String registercode;
	//验证码
	private String smscode;
	//验证码
	private String smstime;
	
	@Param(description = "密码", minLength = 6, maxLength = 16, nullable = false)
	private String password;




	public String getRegistercode() {
		return registercode;
	}

	public void setRegistercode(String registercode) {
		this.registercode = registercode;
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

	public String getSmstime() {
		return smstime;
	}

	public void setSmstime(String smstime) {
		this.smstime = smstime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
