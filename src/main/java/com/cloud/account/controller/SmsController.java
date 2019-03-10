package com.cloud.account.controller;

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
import com.cloud.account.service.UserCompanyService;
import com.cloud.account.vo.LoginVo;
import com.cloud.account.vo.UserAdminVo;
import com.cloud.account.vo.UserCompanyVo;
import com.cloud.util.StringUtils;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.http.result.MsgConstants;


/**
 * @描述: 登录控制层
 * @作者: wm
 * 
 * @作成时间: 2019年3月8日 下午3:39:19
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
	 private Logger logger = LoggerFactory.getLogger(SmsController.class);
	 @Resource(name = "userCompanyServiceImpl")
	 UserCompanyService userCompanyService;
	 
	 /**
	     * @描述: 新建账户
	     * @作者: wm
	     * @时间: 2019年3月7日 下午3:40:35
	     * @param 
	     */
	    @RequestMapping(value = "/ucpaas_callback", method = RequestMethod.POST)
	    @ResponseBody
	    public Object add(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) {
	    	UserCompanyVo queryVo = null;
	        try {
	        	if (StringUtils.isBlank(info)) {
					 return BaseResp.fail(MsgConstants.PARAMS_IS_NULL_CODE, MsgConstants.PARAMS_IS_NULL_MSG);
				}
	        	queryVo = JSON.parseObject(info, UserCompanyVo.class);
	            return userCompanyService.add(queryVo);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.info("UserCompanyController#add FAIL" + e);
	            return BaseResp.fail("999999", e.getMessage());
	        }
	    }
	    
	}