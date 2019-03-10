package com.cloud.util.enums;

/**
 * 常用的数据类型枚举
 * 
 * @author
 *
 */
public enum RegexType {

	NONE, // 不需要验证
	EMAIL, // 邮箱地址
	IP, // ip地址
	TELPHONENUMBER, // 手机+固话
	TELPHONENUMBER_, // 固话+手机+带有-
	PHONENUMBER, // 手机
	TELNUMBER, // 固话
	ID, // 身份证
	LICENSE, // 企业证照号码
	ZIP, // 邮编
	IMG, // 图片
	PASSPORT, // 护照号码
	ORGANIZATION, // 组织结构代码
	NUMBER, // 数字
	SPECIALCHAR, // 不能含有特殊字符
	CHAR, // 字符（a-zA-Z）
	DXSX, // DX/SX
	A12, // 1，2
	UPPERCHAR, // 大写字符
	UPPERCHARNUMBER, // 大写字符+数字
	CHARNUMBER, // 字母+数字
	LOWERCHAR, // 小写字符
	MONEY, // 二位小数
	CHINESE, YYYYMMDDHHMMSS, // 年月日时分秒YYYYMMDDHHMMSS
	YYYYZMMZDD, // YYYY-MM-DD
	YYYYZMMZDDXHHZMMZSS, // 年月日时分秒YYYY-MM-DD HH:MM:SS
	TEFU, // 特服号码
	TELPHONENUMBERTEFU, // 固话+手机+特服号码
	MOMT;
}