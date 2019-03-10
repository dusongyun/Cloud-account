package com.cloud.util.task;

import java.io.InputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cloud.util.auth.annotation.IOFile;

@Component 
public class TaskUtil{
	private static final Logger loger = Logger.getLogger(TaskUtil.class);
	private static InputStream is;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stubAppId

	 String AppId = "wxaee47cc58ee122fb";
     String AppSecret = "2d6765d4d7b48f4d55e7a0781d0eb6a3";
     new TaskUtil().accesstoken2Redis();
	}

//		
	 /**
	  * 定时每小时读取accesstoken并存入redis
	  */
//	 	@Scheduled(cron = "0 0 * * * ?")
		public void accesstoken2Redis() {
	 		try {
			    String accesstoken = IOFile.readProperty("ACCESS_TOKEN");
			    System.out.println(new Date()+"*********"+accesstoken);
	 		}catch(Exception e) {
	 			e.printStackTrace();
	 		}
	 		
			/*Object obj = redisCache.get("accesstoken");
			System.out.println("accesstoken========>>>>>>"+obj);*/
		}

}
