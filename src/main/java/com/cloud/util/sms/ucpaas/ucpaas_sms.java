package com.cloud.util.sms.ucpaas;

import com.alibaba.fastjson.JSON;
import com.cloud.util.date.DateUtil;
import com.cloud.util.http.HttpClientHelper;
import com.cloud.util.sms.ucpaas.vo.TemplateRequest;

public class ucpaas_sms {
	public static void main(String args[]) {
		sendsms();
//		sendsms_batch();
//		addsmstemplate();
//		getsmstemplate();
//		editsmstemplate();
//		deletesmstemplate();
	}
	/**
	 * 指定模板单发
	 */
	public static void sendsms() {  
		TemplateRequest param = new TemplateRequest();
		param.setSid("7a03486476d2ec4cd49321aa0b520ec3");
        param.setToken("36b2e7f47fda66c4d24aede5eee05dc5");
		param.setAppid("d78748ac94b642b3b3aa08cbefa6294d");
		param.setTemplateid("439294");//短信模板名称，限6个汉字或20个数字、英文字、符号	注册验证
		param.setParam("云账户,雷sir,62846309");//模板中的替换参数，如该模板不存在参数则无需传该参数或者参数为空，如果有多个参数则需要写在同一个字符串中，以英文逗号分隔 （如：“a,b,c”），参数中不能含有特殊符号“【】”和“,”	87828,3
		param.setMobile("15021518196");	//接收的单个手机号，暂仅支持国内号码	15021518196
		param.setUid(DateUtil.UUID());//用户透传ID，随状态报告返回	2d92c6132139467b989d087c84a365d8

		String res = null;
        try {
	        res = HttpClientHelper.sendPostByJson(
	        		"https://open.ucpaas.com/ol/sms/sendsms", JSON.toJSONString(param));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		System.out.println("返回结果：" + res);
	}
	/**
	 * 指定模板群发
	 */
	public static void sendsms_batch() {  
		TemplateRequest param = new TemplateRequest();
		param.setSid("7a03486476d2ec4cd49321aa0b520ec3");
        param.setToken("36b2e7f47fda66c4d24aede5eee05dc5");
		param.setAppid("d78748ac94b642b3b3aa08cbefa6294d");
		param.setTemplateid("439101");//短信模板名称，限6个汉字或20个数字、英文字、符号	注册验证
		param.setParam("123456");//模板中的替换参数，如该模板不存在参数则无需传该参数或者参数为空，如果有多个参数则需要写在同一个字符串中，以英文逗号分隔 （如：“a,b,c”），参数中不能含有特殊符号“【】”和“,”	87828,3
		param.setMobile("18017373806,13476074055");	//接收的单个手机号，暂仅支持国内号码	18011984299
		param.setUid(DateUtil.UUID());//用户透传ID，随状态报告返回	2d92c6132139467b989d087c84a365d8
		String res = null;
        try {
	        res = HttpClientHelper.sendPostByJson(
	        		"https://open.ucpaas.com/ol/sms/sendsms_batch", JSON.toJSONString(param));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		System.out.println("返回结果：" + res);
	}
	
	/**
	 * 增加模板
	 */
	public static void addsmstemplate() {  
		TemplateRequest param = new TemplateRequest();
		param.setSid("7a03486476d2ec4cd49321aa0b520ec3");
        param.setToken("36b2e7f47fda66c4d24aede5eee05dc5");
		param.setAppid("d78748ac94b642b3b3aa08cbefa6294d");
		param.setType("4");//短信类型：0:通知短信、5:会员服务短信（需企业认证）、4:验证码短信(此类型content内必须至少有一个参数{1})	4
		param.setTemplate_name("短信验证码");//短信模板名称，限6个汉字或20个数字、英文字、符号	注册验证
		param.setAutograph("云账户");//短信签名，建议使用公司名/APP名/网站名，限2-12个汉字、英文字母和数字，不能纯数字	云之讯
		param.setContent("您的注册验证码是{1},5分钟内有效");//短信内容，最长500字，不得含有【】符号，可支持输入参数，参数示例“{1}”、“{2}”	您的注册验证码是{1}

		String res = null;
        try {
	        res = HttpClientHelper.sendPostByJson(
	        		"https://open.ucpaas.com/ol/sms/addsmstemplate", JSON.toJSONString(param));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		System.out.println("返回结果：" + res);
	}
	/**
	 * 查询模板
	 */
	public static void getsmstemplate() {  
		TemplateRequest param = new TemplateRequest();
		param.setSid("7a03486476d2ec4cd49321aa0b520ec3");
        param.setToken("36b2e7f47fda66c4d24aede5eee05dc5");
		param.setAppid("d78748ac94b642b3b3aa08cbefa6294d");
		param.setTemplateid(null);//模板ID，如不指定则返回该应用下所有模板，如指定则page_num和page_size无效	62222
		param.setPage_num("1");//选填	页码，默认值为1	1
		param.setPage_size("10");//选填	每页个数，最大100个，默认30个	30
		String res = null;
        try {
	        res = HttpClientHelper.sendPostByJson(
	        		"https://open.ucpaas.com/ol/sms/getsmstemplate", JSON.toJSONString(param));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		System.out.println("返回结果：" + res);
	}
	
	/**
	 * 编辑模板
	 */
	public static void editsmstemplate() {  
		TemplateRequest param = new TemplateRequest();
		param.setSid("7a03486476d2ec4cd49321aa0b520ec3");
        param.setToken("36b2e7f47fda66c4d24aede5eee05dc5");
		param.setAppid("d78748ac94b642b3b3aa08cbefa6294d");
		param.setTemplateid("439909");//模板ID，如不指定则返回该应用下所有模板，如指定则page_num和page_size无效	62222
		param.setType("4");//短信类型：0:通知短信、5:会员服务短信（需企业认证）、4:验证码短信(此类型content内必须至少有一个参数{1})	4
		param.setTemplate_name("短信验证码");//短信模板名称，限6个汉字或20个数字、英文字、符号	注册验证
		param.setAutograph("云账户");//短信签名，建议使用公司名/APP名/网站名，限2-12个汉字、英文字母和数字，不能纯数字	云之讯
		param.setContent("您的验证码是{1}，请在5分钟内正确输入");//短信内容，最长500字，不得含有【】符号，可支持输入参数，参数示例“{1}”、“{2}”	您的注册验证码是{1}
		
		String res = null;
        try {
	        res = HttpClientHelper.sendPostByJson(
	        		"https://open.ucpaas.com/ol/sms/editsmstemplate", JSON.toJSONString(param));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		System.out.println("返回结果：" + res);
	}
	/**
	 * 删除模板
	 */
	public static void deletesmstemplate() {  
		TemplateRequest param = new TemplateRequest();
		param.setSid("7a03486476d2ec4cd49321aa0b520ec3");
        param.setToken("36b2e7f47fda66c4d24aede5eee05dc5");
		param.setAppid("d78748ac94b642b3b3aa08cbefa6294d");
		param.setTemplateid("439290");//模板ID，如不指定则返回该应用下所有模板，如指定则page_num和page_size无效	62222
		
		String res = null;
        try {
	        res = HttpClientHelper.sendPostByJson(
	        		"https://open.ucpaas.com/ol/sms/deletesmstemplate", JSON.toJSONString(param));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		System.out.println("返回结果：" + res);
	}
}
