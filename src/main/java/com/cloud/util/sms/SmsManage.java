package com.cloud.util.sms;

/**
 * 短信操作接口
 *
 */
public interface SmsManage {

	/**
	 * 给手机发送的短信
	 * 
	 * @param mobile
	 *            手机号
	 * @param text
	 *            短信内容
	 * @return
	 * @throws Exception 
	 */
	public int sendSmsText(String mobile, String text) throws Exception;

	/**
	 * 
	 * <p>
	 * 发送短信模板内容
	 * </p>
	 * 
	 * @author dusai
	 * @date 2017年8月21日 下午1:48:17
	 * @param phone
	 * @param tempalte
	 * @param args
	 * @throws Exception 
	 * @see
	 */
	public int sendSmsTemplateText(String mobile, String tempalte, String[] args) throws Exception;

}
