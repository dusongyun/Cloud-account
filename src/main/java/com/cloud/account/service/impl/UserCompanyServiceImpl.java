package com.cloud.account.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cloud.account.entity.UserCompany;
import com.cloud.account.mapper.UserCompanyMapper;
import com.cloud.account.service.UserCompanyService;
import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserCompanyVo;
import com.cloud.util.RandomUtils;
import com.cloud.util.StringUtils;
import com.cloud.util.date.DateUtil;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.http.result.MsgConstants;
import com.cloud.util.sms.SmsManage;
import com.cloud.util.sms.template.SmsTemplate;

@Service
public class UserCompanyServiceImpl implements UserCompanyService {
	 @Resource(name = "userCompanyMapper")
	    private UserCompanyMapper userCompanyMapper;
	 @Resource(name = "smsManageImpl")
	    SmsManage smsManage;
	 @Override
	    public BaseResp add(UserCompanyVo user) {
	        try {
	            if (user==null) {
                    return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            } 
	            if (StringUtils.isBlank(user.getCompanyName()) || StringUtils.isBlank(user.getCompanyCode()) 
	            		|| StringUtils.isBlank(user.getCorporationName()) || StringUtils.isBlank(user.getCorporationIdno())
        				|| StringUtils.isBlank(user.getCorporationPhone()) || StringUtils.isBlank(user.getCorporationPic1())
						|| StringUtils.isBlank(user.getCorporationPic2())) {
	            	 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            UserCompany userCompany = new UserCompany();
	            BeanUtils.copyProperties(user,userCompany);
	            userCompany.setId("C"+DateUtil.UUID());
	            userCompany.setPassword(null);
	            String smsVerifyCode = RandomUtils.number(8);
	            userCompany.setRegisterCode(smsVerifyCode);
	            userCompany.setUsername(user.getCompanyCode());
	            if(StringUtils.isBlank(user.getManagerPhone())){
	            	 userCompany.setMobile(user.getCorporationPhone());
	            }else{
	            	userCompany.setMobile(user.getManagerPhone());
	            }
	            userCompany.setStatus(2);//待审核
	            // 查询的entity
	            int record = userCompanyMapper.insertSelective(userCompany);
	            // 用户不存在
	            if (record > 0) {
	            	// 发送注册码
                    smsManage.sendSmsTemplateText(userCompany.getMobile(), SmsTemplate.smsVerifyCode_template,
                                new String[] { smsVerifyCode });
	                return BaseResp.success(userCompany);
	            } else {
	            	return BaseResp.fail(MsgConstants.LOGIN_USER_ADD_ERROR_CODE, MsgConstants.LOGIN_USER_ADD_ERROR_MSG);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	 	@Override
	    public BaseResp firstlogin(LoginVo login) {
	        try {
	        	if (login==null) {
                 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            if (StringUtils.isBlank(login.getUsername()) || StringUtils.isBlank(login.getPassword()) 
	            		|| StringUtils.isBlank(login.getSmscode()) || StringUtils.isBlank(login.getRegistercode())) {
	            	 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            login.setSmstime(DateUtil.dateToStr(new Date(), 10));
	            String password =  login.getPassword();
	            login.setPassword(null);
	            // 查询的entity
	            UserCompany record = userCompanyMapper.selectUserInfo(login);
	            // 用户不存在
	            if (record == null) {
	                return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
	            } else {
	            	if(record.getStatus()!=1){
	            		return BaseResp.fail(MsgConstants.LOGIN_USER_POWER_ERROR_CODE, MsgConstants.LOGIN_USER_POWER_ERROR_MSG);
	            	}
	            	UserCompany userCompany = new UserCompany();
	            	userCompany.setId(record.getId());
	            	userCompany.setPassword(password);
	            	userCompany.setLoginFirst(new Date());
	            	userCompany.setUpdateTime(new Date());
	            	userCompanyMapper.updateByPrimaryKeySelective(userCompany);
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
	    public BaseResp login(LoginVo login) {
	        try {
	        	if (login==null) {
                    return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            if (StringUtils.isBlank(login.getUsername()) || StringUtils.isBlank(login.getPassword()) 
	            		|| StringUtils.isBlank(login.getSmscode())) {
	            	 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
	            }
	            login.setSmstime(DateUtil.dateToStr(new Date(), 10));
	            LoginVo query1 = new LoginVo();
	            query1.setUsername(login.getUsername());
	            UserCompany userCompany = userCompanyMapper.selectUserInfo(query1);
	            if (userCompany == null) {
	                return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
	            } else {
	            	if(userCompany.getStatus()!=1){
	            		return BaseResp.fail(MsgConstants.LOGIN_USER_POWER_ERROR_CODE, MsgConstants.LOGIN_USER_POWER_ERROR_MSG);
	            	}
	            	if(StringUtils.isNotBlank(userCompany.getPassword()) && userCompany.getLoginFirst()!=null){
	            		   // 查询的entity
	    	            UserCompany record = userCompanyMapper.selectUserInfo(login);
	    	            // 用户不存在
	    	            if (record == null) {
	    	                return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
	    	            } else {
	    	                	record.setSmscode("");
	    	                	record.setPassword("");
	    	                    return BaseResp.success(record);
	    	            }
	            	}else{
	            		return BaseResp.success_ext(1,"用户首次登陆");
	            	}
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
				UserCompany record = userCompanyMapper.selectUserInfo(login);
				// 用户不存在
				if (record == null) {
					 return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
				} else {
					if(record.getStatus()!=1){
						return BaseResp.fail(MsgConstants.LOGIN_USER_POWER_ERROR_CODE, MsgConstants.LOGIN_USER_POWER_ERROR_MSG);
					}
					if(StringUtils.isBlank(record.getPassword()) || record.getLoginFirst()==null){
						return BaseResp.success_ext(1,"用户首次登陆");
					}else{
						return BaseResp.success();
					}
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
				 UserCompany record = userCompanyMapper.selectUserInfo(login);
				 // 用户不存在
				 if (record == null) {
					 return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
				 } else {
				  if(StringUtils.isNotBlank(record.getMobile())){
					  	// 发送短信:6位随机数字
			            String smsVerifyCode = RandomUtils.number(6);
			            record.setSmscode(smsVerifyCode);
			            record.setSmstime(DateUtil.addMinute(5));
			            userCompanyMapper.updateByPrimaryKey(record);
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
				UserCompany record = userCompanyMapper.selectByPrimaryKey(login.getId());
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
	    public BaseResp resetPassword(LoginVo login) {
	 		try {
	 			if (login==null) {
	                 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				 }
				if (StringUtils.isBlank(login.getId()) || StringUtils.isBlank(login.getSmscode())
						|| StringUtils.isBlank(login.getPassword())) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
				LoginVo query = new LoginVo();
				query.setId(login.getId());
				query.setSmscode(login.getSmscode());
				query.setSmstime(DateUtil.dateToStr(new Date(), 10));
				UserCompany record = userCompanyMapper.selectUserInfo(query);
				// 用户不存在
				if (record == null) {
					 return BaseResp.fail(MsgConstants.LOGIN_USER_QUERY_ERROR_CODE, MsgConstants.LOGIN_USER_QUERY_ERROR_MSG);
				} else {
					UserCompany up = new UserCompany();
					up.setId(login.getId());
					up.setPassword(login.getPassword());
					up.setUpdateTime(new Date());
					userCompanyMapper.updateByPrimaryKeySelective(up);
				    return BaseResp.success();
				}
			  } catch (Exception e) {
		            e.printStackTrace();
		            return BaseResp.fail("999999", e.getMessage());
			  }
	    }
	 	
}
