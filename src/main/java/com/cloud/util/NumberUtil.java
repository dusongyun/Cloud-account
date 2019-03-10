package com.cloud.util;

public class NumberUtil {
	public static boolean Isnumber(String info){
		String regx = "^[\u4e00-\u9fa5]*$";
		String regx1 = "^\\d+$";
		if(info.matches(regx1)){
//			loger.info("*****是数字****");
			return true;
		}else if(info.matches(regx)){
//			loger.info("****是汉字*****");
			return false;
		}else{
//			loger.info("****无匹配信息*****");
			return false;
		}
		
		
	}
}
