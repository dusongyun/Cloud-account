package com.cloud.util.sms.ucpaas.vo;

public class TemplateRequest {
	private String sid;//	String	必填	用户的账号唯一标识“Account Sid”，在开发者控制台获取（位置）	39467b989d087c2d92c6132184a365d8
	private String token;//	String	必填	用户密钥“Auth Token”，在开发者控制台获取（位置）	23f757bad208226ec301e117e40006ed
	private String appid;//	String	必填	创建应用时系统分配的唯一标示（位置）	2d92c6132139467b989d087c84a365d8
	private String type;//	String	必填	短信类型：0:通知短信、5:会员服务短信（需企业认证）、4:验证码短信(此类型content内必须至少有一个参数{1})	4
	private String template_name;//	String	选填	短信模板名称，限6个汉字或20个数字、英文字、符号	注册验证
	private String autograph;//	String	必填	短信签名，建议使用公司名/APP名/网站名，限2-12个汉字、英文字母和数字，不能纯数字	云之讯
	private String content;//	String	必填	短信内容，最长500字，不得含有【】符号，可支持输入参数，参数示例“{1}”、“{2}”	您的注册验证码是{1}
	private String templateid;//	String	必填	模板ID，如不指定则返回该应用下所有模板，如指定则page_num和page_size无效	62222
	private String page_num;//	String	选填	页码，默认值为1	1
	private String page_size;//	String	选填	每页个数，最大100个，默认30个	30
	private String param;//	String	选填	模板中的替换参数，如该模板不存在参数则无需传该参数或者参数为空，如果有多个参数则需要写在同一个字符串中，以英文逗号分隔 （如：“a,b,c”），参数中不能含有特殊符号“【】”和“,”	87828,3
	private String mobile;//	String	必填	接收的单个手机号，暂仅支持国内号码	18011984299
	private String uid;//	String	选填	用户透传ID，随状态报告返回	2d92c6132139467b989d087c84a365d8

	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	public String getPage_num() {
		return page_num;
	}
	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}
	public String getPage_size() {
		return page_size;
	}
	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
