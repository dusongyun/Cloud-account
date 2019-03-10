package com.cloud.util.sms.ucpaas.vo;

public class TemplateResponse {
	private String code;//	String	状态码	0
	private String msg;//	String	对应状态码的信息	例如：“模板短信整体长度不能超过500位”
	private String templateid;//	String	模板ID	62222
	private String create_date;//	String	创建时间,格式YYYY-MM-DD hh:mm:ss	2017-08-28 19:08:28
	private String report_date;//	String	审核时间	2017-08-28 19:08:28
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

}
