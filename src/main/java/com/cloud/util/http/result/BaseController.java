package com.cloud.util.http.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 基类action
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dusai,date:2017年3月29日 上午11:12:13,content
 * </p>
 * 
 * @author dusai
 * @date 2017年3月29日 上午11:12:13
 * @since
 * @version
 */
public class BaseController {

	protected Map<String, Object> success(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", MsgConstants.API_DEFAULT_SUCCESS_CODE);
		map.put("message", MsgConstants.API_DEFAULT_SUCCESS_MSG);
		map.put("data", object);
		return map;
	}

	protected Map<String, Object> successMessage() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", MsgConstants.API_DEFAULT_SUCCESS_CODE);
		map.put("message", MsgConstants.API_DEFAULT_SUCCESS_MSG);
		return map;
	}

	protected Map<String, Object> fail(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", MsgConstants.API_DEFAULT_FAIL_CODE);
		map.put("data", object);
		return map;
	}

	protected Map<String, Object> failMessage(String code, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message);
		return map;
	}

	protected Map<String, Object> noLogin(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "99");
		map.put("data", object);
		return map;
	}
}
