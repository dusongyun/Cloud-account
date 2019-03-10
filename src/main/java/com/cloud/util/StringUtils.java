package com.cloud.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class StringUtils {

	public StringUtils() {
		super();
	}

	public static String random(int num) {
		String str = "";
		for (int i = 0; i < num; i++) {
			Random rdm = new Random();
			int intRd = Math.abs(rdm.nextInt()) % 10;
			str = str + intRd;

		}
		return str;
	}

	/**
	 * oldStr串中替换成m，结束位置endIndex,总长度不变 用于int右对齐
	 */
	public static String replace(String oldStr, long m) {

		if (oldStr == null || oldStr.length() < 1 || m == 0)
			return oldStr;
		byte[] oldBytes = oldStr.getBytes();
		byte[] newBytes = String.valueOf(m).getBytes();
		int n = oldStr.length() - newBytes.length;
		if (n < 0)
			return null;
		try {
			for (int i = 0; i < newBytes.length; i++) {
				byte c = newBytes[i];
				oldBytes[n + i] = c;
			}
		} catch (Exception e) {
			return null;
		}
		return new String(oldBytes);
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	/**
	 * str不等于"null"、"undefined"、不为null、长度不为0
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 * 将字符串有某种编码转变成另一种编码
	 * 
	 * @param string
	 *            编码的字符串
	 * @param originCharset
	 *            原始编码格式
	 * @param targetCharset
	 *            目标编码格式
	 * @return String 编码后的字符串
	 */
	public static String encodeString(String string, Charset originCharset, Charset targetCharset) {
		return string = new String(string.getBytes(originCharset), targetCharset);
	}

	/**
	 * URL编码
	 * 
	 * @param string
	 *            编码字符串
	 * @param charset
	 *            编码格式
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String encodeUrl(String string, String charset) {
		if (null != charset && !charset.isEmpty()) {
			try {
				return URLEncoder.encode(string, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return URLEncoder.encode(string);
	}

	/**
	 * URL编码
	 * 
	 * @param string
	 *            解码字符串
	 * @param charset
	 *            解码格式
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String decodeUrl(String string, String charset) {
		if (null != charset && !charset.isEmpty()) {
			try {
				return URLDecoder.decode(string, charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return URLDecoder.decode(string);
	}

	/**
	 * 判断字符串是否是空的 方法摘自commons.lang
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * 判断字符串是否是""," ",null,注意和isEmpty的区别
	 * </p>
	 * 方法摘自commons.lang
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0 || "null".equals(str) || "undefined".equals(str)) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals(""))) {
			return true;
		} else if (obj instanceof Short && ((Short) obj).shortValue() == 0) {
			return true;
		} else if (obj instanceof Integer && ((Integer) obj).intValue() == 0) {
			return true;
		} else if (obj instanceof Double && ((Double) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Float && ((Float) obj).floatValue() == 0) {
			return true;
		} else if (obj instanceof Long && ((Long) obj).longValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}

}
