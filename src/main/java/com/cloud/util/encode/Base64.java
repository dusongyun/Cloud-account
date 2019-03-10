package com.cloud.util.encode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
public class Base64 {  
	private static final Logger loger = Logger.getLogger(Base64.class);
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();  
    /** 
     * data[]进行编码 
     * @param data 
     * @return 
     */  
        public static String encode(byte[] data) {  
            int start = 0;  
            int len = data.length;  
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);  
  
            int end = len - 3;  
            int i = start;  
            int n = 0;  
  
            while (i <= end) {  
                int d = ((((int) data[i]) & 0x0ff) << 16)  
                        | ((((int) data[i + 1]) & 0x0ff) << 8)  
                        | (((int) data[i + 2]) & 0x0ff);  
  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append(legalChars[(d >> 6) & 63]);  
                buf.append(legalChars[d & 63]);  
  
                i += 3;  
  
                if (n++ >= 14) {  
                    n = 0;  
                    buf.append(" ");  
                }  
            }  
  
            if (i == start + len - 2) {  
                int d = ((((int) data[i]) & 0x0ff) << 16)  
                        | ((((int) data[i + 1]) & 255) << 8);  
  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append(legalChars[(d >> 6) & 63]);  
                buf.append("=");  
            } else if (i == start + len - 1) {  
                int d = (((int) data[i]) & 0x0ff) << 16;  
  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append("==");  
            }  
  
            return buf.toString();  
        }  
  
        private static int decode(char c) throws Exception {  
            if (c >= 'A' && c <= 'Z')  
                return ((int) c) - 65;  
            else if (c >= 'a' && c <= 'z')  
                return ((int) c) - 97 + 26;  
            else if (c >= '0' && c <= '9')  
                return ((int) c) - 48 + 26 + 26;  
            else  
                switch (c) {  
                case '+':  
                    return 62;  
                case '/':  
                    return 63;  
                case '=':  
                    return 0;  
                default:
                	throw new Exception("3333333333333"); 
               
                }  
        }  
  
        /** 
         * Decodes the given Base64 encoded String to a new byte array. The byte 
         * array holding the decoded data is returned. 
         * @throws Exception 
         */  
  
        public static synchronized byte[] decode(String s) throws Exception {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] decodedBytes = null;
            try {  
//                decode(s, bos);  
            	if(null!=s&&!"".equals(s)&&s.length()>0){
	            	 int i = 0;  
	                 int len = s.length();  
	     	            while (true) {  
	     	                while (i < len && s.charAt(i) <= ' ')  
	     	                    i++;  
	     	                if (i == len)  
	     	                    break;  
	     	                int tri = (decode(s.charAt(i)) << 18)  
	     	                        + (decode(s.charAt(i + 1)) << 12)  
	     	                        + (decode(s.charAt(i + 2)) << 6)  
	     	                        + (decode(s.charAt(i + 3)));  
	     	                bos.write((tri >> 16) & 255);
	     	                if (s.charAt(i + 2) == '=')  
	     	                    break;  
	     	               bos.write((tri >> 8) & 255);  
	     	                if (s.charAt(i + 3) == '=')  
	     	                    break;  
	     	               bos.write(tri & 255);  
	     	                i += 4;  
	     	            }  
	            	decodedBytes = bos.toByteArray();  
            	}
            } catch (NullPointerException e) {  
            	e.printStackTrace();
            	loger.error("++++Base64--decode--NullPointerException--11-->>+++++++++++--------------"+e.getMessage());
                throw new Exception("1010101010101"); 
            } catch (IOException e) {  
            	loger.error("Base64--decode--IOException--11-->>"+e.getMessage());
            	throw new Exception("1111111111111"); 
            }finally{
            	if(null!=bos){
            		 bos.close(); 
            	}
            }  
            return decodedBytes;  
        }  
  
}
