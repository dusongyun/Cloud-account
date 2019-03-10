package com.cloud.account.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cloud.account.entity.UserAdmin;
import com.cloud.account.service.UserAdminService;
import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserAdminVo;
import com.cloud.util.RandomUtils;
import com.cloud.util.StringUtils;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.http.result.BusinessMsgConstants;
import com.cloud.util.http.result.MsgConstants;


/**
 * @描述: 登录控制层
 * @作者: dusai
 * @作成时间: 2018年4月16日 下午3:39:19
 */
@Controller
@RequestMapping("/user-admin")
public class UserAdminController {
	 private Logger logger = LoggerFactory.getLogger(UserAdminController.class);
	 @Resource(name = "userAdminServiceImpl")
	 UserAdminService userAdminService;
	 
	 /**
	     * @描述: 新建账户
	     * @作者: wm
	     * @时间: 2019年3月7日 下午3:40:35
	     * @param 
	     */
	    @RequestMapping(value = "/add", method = RequestMethod.POST)
	    @ResponseBody
	    public Object add(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) {
	    	UserAdminVo queryVo = null;
	        try {
	        	if (StringUtils.isBlank(info)) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
	        	queryVo = JSON.parseObject(info, UserAdminVo.class);
	            return userAdminService.add(queryVo);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.info("UserAdminController#add FAIL" + e);
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	    /**
	     * @描述: 检查用户名是否在白名单内
	     * @作者: dusai
	     * @时间: 2018年4月16日 下午3:40:35
	     * @param 手机号
	     */
	    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
	    @ResponseBody
	    public Object checkUsername(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) {
	        LoginVo queryVo = null;
	        try {
	        	if (StringUtils.isBlank(info)) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
	        	queryVo = JSON.parseObject(info, LoginVo.class);
	            return userAdminService.selectUsername(queryVo);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.info("UserAdminController#checkUsername FAIL" + e);
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	    
	    /**
	     * @描述: 机构法院端专用：发送短信验证码 新增失效时间60s
	     * @作者: dusai
	     * @时间: 2018年4月16日 下午3:40:35
	     * @param 手机号
	     */
	    @RequestMapping(value = "/sendSmscode", method = RequestMethod.POST)
	    @ResponseBody
	    public Object sendSmscode(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) {
	        // 逻辑处理
	    	LoginVo smsSendVo = null;
	        try {
	        	if (StringUtils.isBlank(info)) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
	            smsSendVo = JSON.parseObject(info, LoginVo.class);
	            return userAdminService.sendSmscode(smsSendVo);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.info("UserAdminController#sendSmscode FAIL" + e);
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }

	   
	    /**
	     * @描述: 
	     * @作者: dusai
	     * @时间: 2018年4月16日 下午3:40:35
	     * @param 手机号
	     */
	    @RequestMapping(value = "/login", method = RequestMethod.POST)
	    @ResponseBody
	    public Object login(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) {
	        LoginVo queryVo = null;
	        try {
	        	if (StringUtils.isBlank(info)) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
	        	queryVo = JSON.parseObject(info, LoginVo.class);
	            return userAdminService.login(queryVo, request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.info("UserAdminController#login FAIL" + e);
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	  
	    /**
	     * 用户id获取用户信息
	     *
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value = "/getUserInfo")
	    @ResponseBody
//	    @LoginAuth
	    public Object getUserInfo(@RequestParam(value = "info", required = false) String info, HttpServletRequest request)
	            throws Exception {
	    	LoginVo queryVo = null;
	        try {
	        	if (StringUtils.isBlank(info)) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
	        	queryVo = JSON.parseObject(info, LoginVo.class);
	            return userAdminService.detail(queryVo);
	        } catch (Exception e) {
	            logger.info("UserAdminController#getUserInfo FAIL" + e);
	            e.printStackTrace();
	            return BaseResp.fail("999999", e.getMessage());
	        } finally {
	            // logger.info("输入数据=" + info.toString());
	        }
	    }

	   
	}