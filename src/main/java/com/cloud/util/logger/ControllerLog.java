package com.cloud.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ControllerLog {
//	   private static final BocLogger logger = BocLoggerFactory.getLogger(ControllerLog.class);  
    private static final Logger logger = LoggerFactory.getLogger(ControllerLog.class);
//	private static final Logger logger = Logger.getLogger(ControllerLog.class);
	         
	      public static synchronized void writeFileLogItem(LogItem logItem){
	    	  logger.info(JSON.toJSONString(logItem));  
	      }

}
