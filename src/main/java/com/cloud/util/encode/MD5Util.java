package com.cloud.util.encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;



/**
 * ����MD5������
 * 
 * @author WANGYI854
 */
public class MD5Util {
	
	/**
	 * MD5ժҪ�빤��
	 */
	static MessageDigest md = null;

	// ��ʼ����
	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ne) {
			throw new RuntimeException(ne);
		}

	}

	/**
	 * ��ȡ�����ļ���MD5ֵ��������static���� �����κ��쳣�����ؿ��ַ���
	 * 
	 * @param file
	 *            {@link File} �ļ�
	 * @return String MD5ֵ
	 * @author WANGYI854
	 */
	public static String encryptPWD(File file) {
		String md5Str = "";
		FileInputStream in = null;

		try {
			if (!file.isFile()) {
				return md5Str;
			}

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				md5.update(buffer, 0, len);
			}
			md5Str = new String(Hex.encodeHex(md5.digest())).toUpperCase();

		} catch (Exception e) {
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
		return md5Str;
	}

	/**
	 * ���ַ�������MD5ֵ
	 * 
	 * @param str
	 *            String ��Ҫ����MD5ֵ�Ĵ�
	 * @return String MD5ֵ
	 * @author WANGYI854
	 */
	public static String encryptPWD(String str) {
		byte[] b = str.getBytes();
		md.reset();
		md.update(b);
		byte[] hash = md.digest();
		String d = "";
		for (int i = 0; i < hash.length; i++) {
			int v = hash[i] & 0xFF;
			if (v < 16) {
				d += "0";
			}
			d += Integer.toString(v, 16).toUpperCase();
		}
		return d;
	}
	
	 public static String getMD5(String str) {  
         try {  
             // 生成一个MD5加密计算摘要  
             MessageDigest md = MessageDigest.getInstance("MD5");  
             // 计算md5函数  
             md.update(str.getBytes());  
             // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
             // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
            return new BigInteger(1, md.digest()).toString(16);  
         } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
         }  
     }  
}
