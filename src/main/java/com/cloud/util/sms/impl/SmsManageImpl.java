package com.cloud.util.sms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.util.HexUtil;
import com.cloud.util.HttpClientUtil;
import com.cloud.util.HttpClientUtil.HttpClientResult;
import com.cloud.util.PropObj;
import com.cloud.util.StringUtils;
import com.cloud.util.exception.BusinessException;
import com.cloud.util.sms.SmsManage;

/**
 * @描述: 短信操作实现类
 * @作者: dusai
 * @作成时间: 2018年4月18日 下午3:09:29
 */
@Service
public class SmsManageImpl implements SmsManage {
	Logger logger = LoggerFactory.getLogger(SmsManageImpl.class);
	
    @Autowired
    private PropObj propObj;

	// 暂时没用
	public String templateVerifycode;

	public String getTemplateVerifycode() {
		return templateVerifycode;
	}

	public void setTemplateVerifycode(String templateVerifycode) {
		this.templateVerifycode = templateVerifycode;
	}

	/**
	 * @描述: 短信发送
	 * @作者: dusai
	 * @时间: 2018年4月18日 下午3:09:25
	 */
	@Override
	public int sendSmsText(String mobile, String text)  throws BusinessException, Exception{
		logger.debug("SmsManageImpl#sendSmsText() START");

		try {

			// 发送内容转化成HEX编码字符串
			byte[] bytes = text.getBytes("GBK");
			String message = HexUtil.byte2HexStr(bytes);

			String parameters = "?command=MT_REQUEST&spid="
					+ propObj.getSmsSpid() + "&sppassword="
					+ propObj.getSmsSppassword() + "&da=86"
					+ mobile + "&dc=" + propObj.getSmsDc()
					+ "&sm=" + message;

			HttpClientResult result = HttpClientUtil.httpClientOfGet(
			        propObj.getSmsUri(), parameters);
			if (StringUtils.isNotBlank(result.getValue())) {
				String respText = result.getValue();
				int mterrcodeIndex = respText.lastIndexOf("=");
				// mterrcode返回状态值
				String code = respText.substring(mterrcodeIndex + 1);
				if ("000".equals(code)) {
					logger.debug("短信发送成功! 短信发送人:" + mobile);
				} else {
					int mtstatIndex = respText.lastIndexOf("mtstat");
					throw new BusinessException(code, "短信发送失败！"
							+ respText.substring(mtstatIndex));
				}
			} else {
				throw new BusinessException("请求发送失败！" + result.toString());
			}
		} catch (Exception e) {
			logger.error("短信发送失败, 短信发送人:" + mobile, e);
			e.printStackTrace();
			throw new Exception("请求发送失败！" + e.getMessage());
		}
		return 0;
	}

	@Override
	public int sendSmsTemplateText(String phone, String tempalte, String[] args) throws Exception {

		String text = String.format(tempalte, args);
		return sendSmsText(phone, text);
	}


	public static void main(String[] args) throws Exception {
		/*
		 * HttpClientResult result = HttpClientUtil .httpClientOfGet(
		 * "http://esms100.10690007.net/sms/mt",
		 * "?command=MT_REQUEST&spid=9147&sppassword=gpw12bh23&da=8618626335736&dc=15&sm=c4e3bac332303038"
		 * );
		 */
		String s = "command=MT_RESPONSE&spid=9147&mtstat=ET:0110&mterrcode=100";
		int index = s.lastIndexOf("mtstat");
		int mterrcodeIndex = s.lastIndexOf("=");

		System.out.println("====>>>" + s.substring(index));
		System.out.println("====>>>" + s.substring(mterrcodeIndex + 1));
		
		
		// String.format
		
	}

}
