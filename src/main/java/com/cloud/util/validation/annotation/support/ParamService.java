package com.cloud.util.validation.annotation.support;

import java.lang.reflect.Field;

import com.cloud.util.StringUtils;
import com.cloud.util.enums.RegexType;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.http.result.MsgConstants;
import com.cloud.util.regex.RegexUtils;
import com.cloud.util.validation.annotation.Param;

/**
 * 注解解析
 * 
 * @author
 */
public class ParamService {

	public ParamService() {
		super();
	}
	// 解析的入口
		public static boolean validParam(Object object) throws Exception {
			// 获取object的类型
			Class<? extends Object> clazz = object.getClass();
			// 获取该类型声明的成员
			Field[] fields = clazz.getDeclaredFields();
			// 遍历属性
			for (Field field : fields) {
				// 对于private私有化的成员变量，通过setAccessible来修改器访问权限
				field.setAccessible(true);
				String msg = validate(field, object);
				// 重新设置会私有权限
				field.setAccessible(false);
				if (msg != null) {
//					result = BaseResp.fail(MsgConstants.API_FAIL_NULL, msg);
					return false;
				}
			}
			return true;
		}

	// 解析的入口
	public static BaseResp valid(Object object) throws Exception {
		BaseResp result = BaseResp.defaultSuccess();
		// 获取object的类型
		Class<? extends Object> clazz = object.getClass();
		// 获取该类型声明的成员
		Field[] fields = clazz.getDeclaredFields();
		// 遍历属性
		for (Field field : fields) {
			// 对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			String msg = validate(field, object);
			// 重新设置会私有权限
			field.setAccessible(false);
			if (msg != null) {
				result = BaseResp.fail(MsgConstants.API_FAIL_NULL, msg);
				return result;
			}
		}
		return result;
	}

	/**
	 * 判断参数
	 * 
	 * @param field
	 * @param object
	 * @throws Exception
	 */
	public static String validate(Field field, Object object) throws Exception {

		String description;
		Object value;

		// 获取对象的成员的注解信息
		Param param = field.getAnnotation(Param.class);
		value = field.get(object);

		if (param == null)
			return null;

		description = param.description().equals("") ? field.getName() : param.description();

		/************* 注解解析工作开始 ******************/
		if (!param.nullable()) {
			if (value == null || StringUtils.isBlank(value.toString())) {
				return description + "不能为空";
			}
		}

		if (!StringUtils.isNullOrEmpty(value) && value.toString().length() > param.maxLength()
				&& param.maxLength() != 0) {
			return description + "长度不能超过" + param.maxLength();
		}

		if (!StringUtils.isNullOrEmpty(value) && value.toString().length() < param.minLength()
				&& param.minLength() != 0) {
			return description + "长度不能小于" + param.minLength();
		}

		String result = null;

		if (param.regexType() != RegexType.NONE) {
			switch (param.regexType()) {
			case NONE:
				break;
			case SPECIALCHAR:
				if (!StringUtils.isNullOrEmpty(value) && RegexUtils.hasSpecialChar(value.toString())) {
					result = description + "不能含有特殊字符";
				}
				break;
			case CHINESE:
				if (!StringUtils.isNullOrEmpty(value) && RegexUtils.isChinese2(value.toString())) {
					result = description + "不能含有中文字符";
				}
				break;
			default:
				if (!StringUtils.isNullOrEmpty(value) && !RegexUtils.isTrue(param.regexType(), value.toString())) {
					result = description + "格式不正确";
				}
				break;
			}
		}

		if (param != null && StringUtils.isNotEmpty(param.regexExpression()) && !param.regexExpression().equals("")) {
			if (!StringUtils.isNullOrEmpty(value) && !value.toString().matches(param.regexExpression())) {
				result = description + "格式不正确";
			}
		}

		return result;
		/************* 注解解析工作结束 ******************/
	}
}
