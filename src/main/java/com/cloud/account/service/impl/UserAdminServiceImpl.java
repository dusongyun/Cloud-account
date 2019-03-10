package com.cloud.account.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cloud.account.entity.UserAdmin;
import com.cloud.account.mapper.UserAdminMapper;
import com.cloud.account.service.UserAdminService;
import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserAdminVo;
import com.cloud.util.RandomUtils;
import com.cloud.util.StringUtils;
import com.cloud.util.date.DateUtil;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.http.result.MsgConstants;
import com.cloud.util.sms.SmsManage;
import com.cloud.util.sms.template.SmsTemplate;

@Service
public class UserAdminServiceImpl implements UserAdminService {
	 @Resource(name = "userAdminMapper")
	    private UserAdminMapper userAdminMapper;
	 @Resource(name = "smsManageImpl")
	    SmsManage smsManage;
	 @Override
	    public BaseResp add(UserAdminVo user) {
	        try {
	            if (user==null) {
                    return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            } 
	            if (StringUtils.isBlank(user.getUsername()) && StringUtils.isBlank(user.getPassword()) 
	            		&& StringUtils.isBlank(user.getMobile())) {
	            	 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            UserAdmin userAdmin = new UserAdmin();
	            BeanUtils.copyProperties(user,userAdmin);
	            userAdmin.setId("A"+DateUtil.UUID());
	            userAdmin.setPassword(user.getPassword());
	            // 查询的entity
	            int record = userAdminMapper.insertSelective(userAdmin);
	            // 用户不存在
	            if (record > 0) {
	                return BaseResp.success();
	            } else {
	            	return BaseResp.fail(MsgConstants.LOGIN_USER_ADD_ERROR_CODE, MsgConstants.LOGIN_USER_ADD_ERROR_MSG);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	 
	 	@Override
	    public BaseResp login(LoginVo login, HttpServletRequest request, HttpServletResponse response) {
	        try {
	        	if (login==null) {
                    return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            if (StringUtils.isBlank(login.getUsername()) || StringUtils.isBlank(login.getPassword()) 
	            		|| StringUtils.isBlank(login.getSmscode())) {
	            	 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            login.setSmstime(DateUtil.dateToStr(new Date(), 10));
	            // 查询的entity
	            UserAdmin record = userAdminMapper.selectUserInfo(login);
	            // 用户不存在
	            if (record == null) {
	                return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
	            } else {
	                	record.setSmscode("");
	                	record.setPassword("");
	                    return BaseResp.success(record);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	 
	 @Override
	    public BaseResp selectUsername(LoginVo login) {
		  try {
	        // 判断验证码是否正确
			if (login==null) {
              return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
            }
			if (StringUtils.isBlank(login.getUsername())) {
				 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
			}
			login.setPassword(null);
			login.setSmscode(null);
			login.setSmstime(null);
			// 查询的entity
			UserAdmin record = userAdminMapper.selectUserInfo(login);
			// 用户不存在
			if (record == null) {
				 return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
			} else {
			    return BaseResp.success();
			}
		  } catch (Exception e) {
	            e.printStackTrace();
	            return BaseResp.fail("999999", e.getMessage());
          }
	    }
	 
	 	@Override
	    public BaseResp sendSmscode(LoginVo login) {
	 		try {
				 if (login==null) {
	                 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				 }
				 login.setPassword(null);
				 login.setSmscode(null);
				 login.setSmstime(null);
				 // 查询的entity
				 if (StringUtils.isBlank(login.getUsername())) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				 }
				 UserAdmin record = userAdminMapper.selectUserInfo(login);
				 // 用户不存在
				 if (record == null) {
					 return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
				 } else {
				  if(StringUtils.isNotBlank(record.getMobile())){
					  	// 发送短信:6位随机数字
			            String smsVerifyCode = RandomUtils.number(6);
			            record.setSmscode(smsVerifyCode);
			            record.setSmstime(DateUtil.addMinute(5));
			            userAdminMapper.updateByPrimaryKey(record);
			            // 获取验证码，设置验证码失效时间
			            smsManage.sendSmsTemplateText(record.getMobile(), SmsTemplate.smsVerifyCode_template,
				                    new String[] { smsVerifyCode });
						}
					    return BaseResp.success();
			 		}
			  } catch (Exception e) {
		            e.printStackTrace();
		            return BaseResp.fail("999999", e.getMessage());
			  }
	 	}
	 	
	 	@Override
	    public BaseResp detail(LoginVo login) {
	 		try {
	 			if (login==null) {
	                 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				 }
				if (StringUtils.isBlank(login.getId())) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
				UserAdmin record = userAdminMapper.selectByPrimaryKey(login.getId());
				// 用户不存在
				if (record == null) {
					 return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
				} else {
					record.setSmscode("");
	            	record.setPassword("");
				    return BaseResp.success(record);
				}
			  } catch (Exception e) {
		            e.printStackTrace();
		            return BaseResp.fail("999999", e.getMessage());
			  }
	    }
}
