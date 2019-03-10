package com.cloud.util.logger;

public class LogItem implements java.io.Serializable {
	private static final long serialVersionUID = -8076723702434202977L;
	private String logid;//日志id（暂时不启用）
	private String requestip;//请求ip
	private String requestopenid;//请求用户
	private String requesttype;//接口
	private String requestinfo;//请求参数
	private String requesttime;//请求时间
	private String responsecode;//返回码
	private String responsemsg;//返回码说明
	private String responseinfo;//返回数据
	private String userAgent;//消息头
	private String datatime;//接口时延
	
	
	public String getRequestopenid() {
		return requestopenid;
	}

	public void setRequestopenid(String requestopenid) {
		this.requestopenid = requestopenid;
	}

	public String getDatatime() {
		return datatime;
	}

	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}

	public String getResponsemsg() {
		return responsemsg;
	}

	public void setResponsemsg(String responsemsg) {
		this.responsemsg = responsemsg;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getRequestip() {
		return requestip;
	}
	public void setRequestip(String requestip) {
		this.requestip = requestip;
	}
	
	public String getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}
	public String getRequestinfo() {
		return requestinfo;
	}
	public void setRequestinfo(String requestinfo) {
		this.requestinfo = requestinfo;
	}
	public String getRequesttime() {
		return requesttime;
	}
	public void setRequesttime(String requesttime) {
		this.requesttime = requesttime;
	}
	public String getResponsecode() {
		return responsecode;
	}
	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}
	public String getResponseinfo() {
		return responseinfo;
	}
	public void setResponseinfo(String responseinfo) {
		this.responseinfo = responseinfo;
	}

}
