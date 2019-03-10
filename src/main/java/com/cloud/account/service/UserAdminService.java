package com.cloud.account.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserAdminVo;
import com.cloud.util.http.result.BaseResp;


public interface UserAdminService {

	BaseResp login(LoginVo login, HttpServletRequest request, HttpServletResponse response);

	BaseResp selectUsername(LoginVo login);

	BaseResp sendSmscode(LoginVo login);

	BaseResp detail(LoginVo login);

	BaseResp add(UserAdminVo user);

	
}
