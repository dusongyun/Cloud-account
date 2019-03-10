package com.cloud.util.regex;

import com.cloud.util.enums.RegexType;

public enum EnumREGCode {
	
	ISEMAIL("验证邮箱地址","EMAIL","^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"),
	ISIP("ip地址","IP","^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"),
	TELPHONENUMBER("固话+手机","TELPHONENUMBER","^(0{0,1}1[3|4|5|6|7|8|9][0-9]{9})$|^(0\\d{2,3}\\d{7,8})$"),
	TELPHONENUMBER_("固话+手机+带有-","TELPHONENUMBER_","^(0{0,1}1[3|4|5|6|7|8|9][0-9]{9})$|^(0\\d{2,3}-?\\d{7,8})$"),
	ISPHONENUMBER("手机","PHONENUMBER","^(0{0,1}1[3|4|5|6|7|8|9][0-9]{9})$"),
	ISTELNUMBER("固定电话","TELNUMBER","^(0{0,1}1[3|4|5|6|7|8|9][0-9]{9})$"),
	ISID("身份证","ID","(\\d{14}[0-9])|(\\d{17}[0-9Xx])"),
	ISLICENSE("企业证照号码","LICENSE","^\\d{1,20}$"),
	ISZIP("邮政编码","ZIP","^[1-9]\\d{5}(?!\\d)$"),
	ISIMG("图片格式","IMG",".jpg$|.JPG$|.gif$|.GIF$|.png$|.PNG$|.bmp$|.BMP$"),
	ISPASSPORT("护照号码","PASSPORT","^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$"),
	ISORGANIZATION("组织机构代码","ORGANIZATION","^[a-zA-Z0-9]{8}-[a-zA-Z0-9]$"),
	ISNUMBER("数字","NUMBER","[0-9]*"),
	ISCHAR("字母","CHAR","^[a-zA-Z]*"),
	ISUPPERCHAR("大写字母","UPPERCHAR","^[A-Z]*"),
	ISLOWERCHAR("小写字母","LOWERCHAR","^[a-z]*"),
	ISUPPERCHARNUMBER("大写字符+数字", "UPPERCHARNUMBER", "^[A-Z0-9]*"),
	ISCHARNUMBER("字符+数字", "CHARNUMBER", "^[A-Z0-9a-z]*"),
	ISMONEY("金钱","MONEY","^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){2})?$"),
	ISYYYYMMDDHHMMSS("日期格式", "YYYYMMDDHHMMSS", "((20)[1][0-9])(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])([01]?[0-9]|2[0-3])[0-5][0-9][0-5][0-9]"),// 年月日时分秒YYYYMMDDHHMMSS
	ISYYYYZMMZDD("日期格式", "YYYYZMMZDD", "((20)[1][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"),// YYYY-MM-DD
	ISYYYYZMMZDDXHHMMZSS("日期格式", "YYYYZMMZDDXHHZMMZSS", "((20)[1][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])\\s([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]"),// 年月日时分秒YYYY-MM-DD HH-MM-SS
	TEFU("特服号码", "TEFU", "^([4|8]00-?\\d{7})$|^(1010\\d{4})$|^(9\\d{4})$"),//特服号码验证
	TELPHONENUMBERTEFU("固话+手机+特服号码","TELPHONENUMBERTEFU","^(0{0,1}1[3|4|5|6|7|8|9][0-9]{9})$|^(0\\d{2,3}-?\\d{7,8})$|^([4|8]00-?\\d{7})$|^(1010\\d{4})$|^(9\\d{4,8})$");
	
	private String name;
	private String key;
	private String reg;

	EnumREGCode(String name, String key, String reg) {
		this.name = name;
		this.key = key;
		this.reg = reg;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getReg(RegexType key) {
		for (EnumREGCode regCode : EnumREGCode.values()) {
			if (regCode.getKey().equals(key.toString())) {
				return regCode.getReg();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

}
