package com.cloud.util.encode;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 与js加密配套
 * @author jz
 *
 */
public class AESUtilsForJs {
	/**
	 * 加密
	 * @param source
	 * @param key
	 * @return
	 */
    public static String encrypt4AES(String source,String key) { 
    	try { 
//    			key = Md5Util.encode(key);
	    		IvParameterSpec zeroIv = new IvParameterSpec(key.getBytes("utf-8")); 
	    		SecretKeySpec key1 = new SecretKeySpec(key.getBytes("utf-8"), "AES"); 
	    		Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); 
	    		cipher.init(Cipher.ENCRYPT_MODE, key1, zeroIv);
	    		byte[] encryptedData = cipher.doFinal(source.getBytes());
	    		String encryptResultStr = Base64.encode(encryptedData);
	    		String result = encryptResultStr.replaceAll(" ", "");
	    		return result; 
    		/*IvParameterSpec zeroIv = new IvParameterSpec(key.getBytes());
            SecretKeySpec key1 = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key1, zeroIv);
            byte[] encryptedData = cipher.doFinal(source.getBytes());
            String encryptResultStr = Base64.encode(encryptedData);
            return encryptResultStr;*/
    		// 加密 
    		} catch (NoSuchAlgorithmException e) {   
//         		  e.printStackTrace();  
	      } catch (NoSuchPaddingException e) {   
//	              e.printStackTrace();   
	      } catch (InvalidKeyException e) {   
//	              e.printStackTrace();   
	     /* }  catch (IllegalBlockSizeException e) {   
//	              e.printStackTrace();   
	    	  LoggerUtil.error(LoggerUtil.StackTrace()+e.getMessage());
	      } catch (BadPaddingException e) {   
//	              e.printStackTrace();   
	    	  LoggerUtil.error(LoggerUtil.StackTrace()+e.getMessage());*/
	      }  catch (Exception e) { 
		} 
    	return null; 
    } 
    
    /**
	 * 解密
	 * @param source
	 * @param key
	 * @return
	 */
    public static String decrypt4AES(String content,String key) { 
    	try { 
//	    		key = Md5Util.encode(key);
	    		byte[] decryptFrom = Base64.decode(content); 
	    		IvParameterSpec zeroIv = new IvParameterSpec(key.getBytes("utf-8")); 
	    		SecretKeySpec key1 = new SecretKeySpec(key.getBytes("utf-8"), "AES"); 
	    		Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); 
	    		cipher.init(Cipher.DECRYPT_MODE, key1, zeroIv); 
	    		byte decryptedData[] = cipher.doFinal(decryptFrom); 
	    		return new String(decryptedData); // 加密 
    		} catch (NoSuchAlgorithmException e) {   
	           		  e.printStackTrace();  
		      } catch (NoSuchPaddingException e) {   
		              e.printStackTrace();   
		      } catch (InvalidKeyException e) {   
		              e.printStackTrace();   
		      } catch (UnsupportedEncodingException e) {   
		              e.printStackTrace();   
		      } catch (IllegalBlockSizeException e) {   
		              e.printStackTrace();   
		      } catch (BadPaddingException e) {  
		              e.printStackTrace();   
		      }  catch (Exception e) { 
		    	  e.printStackTrace();   
    		} 
    		return null; 
	} 
    /**
     * 数组转换成十六进制字符串
     * @param byte[]
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
     StringBuffer sb = new StringBuffer(bArray.length);
     String sTemp;
     for (int i = 0; i < bArray.length; i++) {
      sTemp = Integer.toHexString(0xFF & bArray[i]);
      if (sTemp.length() < 2)
       sb.append(0);
      sb.append(sTemp.toUpperCase());
     }
     return sb.toString();
    }

public static void main(String[] args) {
//	System.out.println(encrypt4AES("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈", "$#%^&YUHSX#@QWAD"));
	System.out.println(decrypt4AES("wriTJGj6/bWm4IyO4Lxjlg==", "c51d7fe88b393b06"));
	System.out.println(encrypt4AES("123456", "c51d7fe88b393b06"));
}

}
