package com.cloud.util.http.result;

public class MsgConstants {
	
	// api default msg 数据返回成功
	public static final String API_DEFAULT_SUCCESS_CODE = "0000";
	public static final String API_DEFAULT_SUCCESS_MSG = "success";
	
	/**Exception 异常**/
	public static final String API_DEFAULT_FAIL_CODE = "9999";
	public static final String API_DEFAULT_FAIL_MSG = "server is error";
	
	// 操作成功和失败时的状态码
	public static final String SUCCESS_CODE = "0000";
	public static final String SUCCESS_MSG = "success";

	public static final String FAIL_CODE = "-1";
	public static final String FAIL_MSG = "error";
	
	/** 参数错误  **/
	public static final String PARAMS_IS_NULL_CODE = "000001";// 参数为空
	public static final String PARAMS_IS_NULL_MSG = "参数为空";
	
	public static final String PARAMS_NOT_COMPLETE_CODE = "000002"; // 参数不全
	public static final String PARAMS_NOT_COMPLETE_MSG = "参数不全"; // 参数不全
	
	public static final String PARAMS_TYPE_ERROR_CODE = "000003"; // 参数类型错误
	public static final String PARAMS_TYPE_ERROR_MSG = "参数类型错误"; 
	
	public static final String PARAMS_IS_INVALID_CODE = "000004"; // 参数无效
	public static final String PARAMS_IS_INVALID_MSG = "参数无效";
	//登录相关的报错信息
	public static final String LOGIN_USER_ERROR_CODE = "010000"; 
	public static final String LOGIN_USER_ERROR_MSG = "用户信息有误";
	public static final String LOGIN_USER_IS_NULL_CODE = "010001"; 
	public static final String LOGIN_USER_IS_NULL_MSG = "用户为空";
	public static final String LOGIN_USER_ADD_ERROR_CODE = "010002"; 
	public static final String LOGIN_USER_ADD_ERROR_MSG = "用户新增失败";
	public static final String LOGIN_USER_QUERY_ERROR_CODE = "010003"; 
	public static final String LOGIN_USER_QUERY_ERROR_MSG = "用户查询失败";
	public static final String LOGIN_USER_DEL_ERROR_CODE = "010004"; 
	public static final String LOGIN_USER_DEL_ERROR_MSG = "用户删除失败";
	public static final String LOGIN_USER_UP_ERROR_CODE = "010005"; 
	public static final String LOGIN_USER_UP_ERROR_MSG = "用户修改失败";
	public static final String LOGIN_USER_POWER_ERROR_CODE = "010006"; 
	public static final String LOGIN_USER_POWER_ERROR_MSG = "用户权限有误";
	

	/** 数据错误  **/
	public static final String DATA_NOT_FOUND_CODE = "50001"; // 数据未找到
	public static final String DATA_NOT_FOUND_MSG = "数据未找到"; // 数据未找到
	
	public static final String DATA_IS_WRONG_CODE = "50002";// 数据有误
	public static final String DATA_IS_WRONG_MSG = "数据有误";// 数据有误
	
	public static final String DATA_ALREADY_EXISTED_CODE = "50003";// 数据已存在
	public static final String DATA_ALREADY_EXISTED_MSG = "数据已存在";// 数据已存在
	
	public static final String API_UPDATE_FAIL_CODE = "50004";
	public static final String API_UPDATE_FAIL_MSG = "数据更新失败";
	
	public static final String API_INSERT_FAIL_CODE = "50005";
	public static final String API_INSERT_FAIL_MSG = "数据录入失败";
	
	/** 接口错误 **/
	public static final String INTERFACE_INNER_INVOKE_CODE = "60001"; // 系统内部接口调用异常
	public static final String INTERFACE_INNER_INVOKE_MSG = "系统内部接口调用异常";
	
	public static final String INTERFACE_OUTER_INVOKE_CODE = "60002";// 系统外部接口调用异常
	public static final String INTERFACE_OUTER_INVOKE_MSG = "系统外部接口调用异常";
	
	public static final String INTERFACE_FORBIDDEN_CODE = "60003";// 接口禁止访问
	public static final String INTERFACE_FORBIDDEN_CODE_MSG = "接口禁止访问";
	
	public static final String INTERFACE_ADDRESS_INVALID_CODE = "60004";// 接口地址无效
	public static final String INTERFACE_ADDRESS_INVALID_MSG = "接口地址无效";
	
	public static final String INTERFACE_REQUEST_TIMEOUT_CODE = "60005";// 接口请求超时
	public static final String INTERFACE_REQUEST_TIMEOUT_MSG = "接口请求超时";
	
	public static final String INTERFACE_EXCEED_LOAD_CODE = "60006";// 接口负载过高
	public static final String INTERFACE_EXCEED_LOAD_MSG = "接口负载过高";
	
	/**权限错误**/
	public static final String PERMISSION_NO_ACCESS_CODE = "70001";// 没有访问权限
	public static final String PERMISSION_NO_ACCESS_MSG = "没有访问权限";
	
	
	public static final String PERMISSION_NO_IMG_CODE = "80001";// 
	public static final String PERMISSION_NO_IMG_MSG = "该文件不是图片，请从新选择";
	
	/**
	 * 是否返回成功状态值 false
	 */
	public static final String API_FALSE = "false";
	/**
	 * 是否返回成功状态值 true
	 */
	public static final String API_TRUE = "true";
	/**
	 * 0001 参数不全，必填字段为空，参数格式不正确
	 */
	public static final String API_FAIL_NULL = "0001";
	public static final String API_FAIL_NULL_MSG = "参数格式不正确";

	/**
	 * 注册业务错误信息
	 */
	public static final String ERROR_PWD_DIFFER = "REGISTER_0001";
	public static final String ERROR_PWD_DIFFER_MSG = "两次密码输入不一致";
	
	public static final String ERROR_MOBILE_EXIST = "REGISTER_0002";
	public static final String ERROR_MOBILE_EXIST_MSG = "该用户不存在，请联系管理员！";
	
	public static final String ERROR_CAPTCHA = "REGISTER_0003";
	public static final String ERROR_CAPTCHA_MSG = "输入验证码错误";
	
	public static final String ERROR_SMS_NONE = "REGISTER_0004";
	public static final String ERROR_SMS_NONE_MSG = "请输入验证码";
	
	public static final String ERROR_SMS = "REGISTER_0004";
	public static final String ERROR_SMS_MSG = "输入验证码错误";

	/**
	 * 登录业务错误信息
	 */
	public static final String ERROR_USER_NONE = "LOGIN_0001";
	public static final String ERROR_USER_NONE_MSG = "用户不存在";
	
	public static final String ERROR_PWD = "LOGIN_0002";
	public static final String ERROR_PWD_MSG = "密码有误";
	
	public static final String ERROR_LOGIN_TIMEOUT = "LOGIN_0003";
	public static final String ERROR_LOGIN_TIMEOUT_MSG = "登录超时，请重新登录";
	
	public static final String ERROR_LOGIN_UT_NONE = "LOGIN_0004";
	public static final String ERROR_LOGIN_UT_NONE_MSG = "UT参数不存在";
	
	

	/**返回Code**/
	public static final String BIND_SUCCESS_CODE = "70001";
	public static final String BIND_SUCCESS_MSG = "绑定成功";
	
	public static final String BIND_ERROR_CODE = "70002";
	public static final String BIND_ERROR_MSG = "绑定失败";
	
	public static final String UN_BIND_SUCCESS_CODE = "70003";
	public static final String UN_BIND_SUCCESS_MSG = "解除绑定成功";
	
	public static final String UN_BIND_ERROR_CODE = "70004";
	public static final String UN_BIND_ERROR_MSG = "解除绑定失败";

}
