package com.cloud.account.service;

import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserCompanyVo;
import com.cloud.util.http.result.BaseResp;


public interface UserCompanyService {

	BaseResp add(UserCompanyVo user);

	BaseResp firstlogin(LoginVo login);

	BaseResp login(LoginVo login);

	BaseResp selectUsername(LoginVo login);

	BaseResp sendSmscode(LoginVo login);

	BaseResp detail(LoginVo login);

	BaseResp resetPassword(LoginVo login);

}
