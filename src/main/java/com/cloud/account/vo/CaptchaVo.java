package com.cloud.account.vo;

import java.io.Serializable;

import com.cloud.util.validation.annotation.Param;


public class CaptchaVo implements Serializable {
	private static final long serialVersionUID = 8740063960887018514L;
	private String mobile;
	private String verification;

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "CaptchaVo [verification=" + verification + "]";
	}

}