package com.cloud.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cloud.util.enums.RegexType;



/**
 * 常用的正则表达式
 * 
 * @author <a href="http://www.xdemo.org/">http://www.xdemo.org/</a>
 *         444972427@qq.com
 */
public class RegexUtils {
	
	
	/**
	 * 判断符合
	 * 
	 * @param key
	 *            手机号码
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isTrue(RegexType key, String value) {
		if (null == value || "".equals(value))
			return false;
		return value.matches(EnumREGCode.getReg(key));
	}

	/**
	 * 判断是否含有中文，仅适合中国汉字，不包括标点
	 * 
	 * @param text
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isChinese(String text) {
		if (null == text || "".equals(text))
			return false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(text);
		return m.find();
	}

	/**
	 * 判断几位小数(正数)
	 * 
	 * @param decimal
	 *            数字
	 * @param count
	 *            小数位数
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isDecimal(String decimal, int count) {
		if (null == decimal || "".equals(decimal))
			return false;
		String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count
				+ "})?$";
		return decimal.matches(regex);
	}

	/**
	 * 判断是否含有特殊字符
	 * 
	 * @param text
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean hasSpecialChar(String text) {
		if (null == text || "".equals(text))
			return false;
		if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
			// 如果不包含特殊字符
			return true;
		}
		return false;
	}

	/**
	 * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
	 */
	public static boolean isChinese2(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

}
