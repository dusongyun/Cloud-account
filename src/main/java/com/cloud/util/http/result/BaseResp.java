package com.cloud.util.http.result;

import com.alibaba.fastjson.JSON;

public class BaseResp {

	/** 响应代码 */
	private String code;

	/** 响应消息 */
	private String msg;
	
	/** 额外的响应消息 */
	private String msg_ext;
	/** 返回结果对象 */
	private Object data;

	/** request结果集（json） */
	/*private String reqJson;*/

	public String getCode() {
		return code;
	}

	public String getMsg_ext() {
		return msg_ext;
	}

	public void setMsg_ext(String msg_ext) {
		this.msg_ext = msg_ext;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 生成成功返回对象
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp defaultSuccess() {
		BaseResp resp = new BaseResp();
		resp.setCode(MsgConstants.API_DEFAULT_SUCCESS_CODE);
		resp.setMsg(MsgConstants.API_DEFAULT_SUCCESS_MSG);
		return resp;
	}

	/**
	 * 生成失败返回对象
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp defaultFail() {
		BaseResp resp = new BaseResp();
		// 添加字段描述
		resp.setCode(MsgConstants.API_DEFAULT_FAIL_CODE);
		resp.setMsg(MsgConstants.API_DEFAULT_FAIL_MSG);
		return resp;
	}
	
	/**
	 * 录入数据失败
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp insertFail() {
		BaseResp resp = new BaseResp();
		// 添加字段描述
		resp.setCode(MsgConstants.API_INSERT_FAIL_CODE);
		resp.setMsg(MsgConstants.API_INSERT_FAIL_MSG);
		return resp;
	}

	public boolean isSuccess() {
		return code.equalsIgnoreCase(MsgConstants.API_DEFAULT_SUCCESS_CODE);
	}

	/**
	 * 操作成功时返回值
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp success() {
		BaseResp resp = new BaseResp();
		resp.setCode(MsgConstants.SUCCESS_CODE);
		resp.setMsg(MsgConstants.SUCCESS_MSG);
		return resp;
	}
	/**
	 * 操作成功时返回值
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp success_ext(Object data,String msg_ext) {
		BaseResp resp = new BaseResp();
		resp.setCode(MsgConstants.SUCCESS_CODE);
		resp.setMsg(MsgConstants.SUCCESS_MSG);
		resp.setMsg_ext(msg_ext);
		resp.setData(data);
		return resp;
	}
	
	public static BaseResp returncode(String code,String msg, Object date) {
		BaseResp resp = new BaseResp();
		resp.setCode(code);
		resp.setMsg(msg);
		resp.setData(date);
		return resp;
	}
	/**
	 * 操作成功时返回值
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp success(Object data) {
		BaseResp resp = new BaseResp();
		resp.setCode(MsgConstants.SUCCESS_CODE);
		resp.setMsg(MsgConstants.SUCCESS_MSG);
		resp.setData(data);
		return resp;
	}

	/**
	 * 微信Resp消息
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp wechatResp(String code,String msg) {
		BaseResp resp = new BaseResp();
		resp.setCode(code);
		resp.setMsg(msg);
		return resp;
	}

	/**
	 * 操作失败时返回值
	 * 
	 * @param msg
	 * @return
	 */
	public static BaseResp fail(String failCode, String failMsg) {
		BaseResp resp = new BaseResp();
		resp.setCode(failCode);
		resp.setMsg(failMsg);
		return resp;
	}

	public static String getJson(BaseResp baseResp) {
		return JSON.toJSONString(baseResp).toString();
//		return JSONObject.fromObject(baseResp).toString();
	}

	@Override
	public String toString() {
		return "BaseResp [code=" + code + ", msg=" + msg + ", msg_ext=" + msg_ext + ", data=" + data + "]";
	}

	/*public String getReqJson() {
		return reqJson;
	}

	public void setReqJson(String reqJson) {
		this.reqJson = reqJson;
	}*/

}
