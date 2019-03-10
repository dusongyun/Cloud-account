package com.cloud.util.auth.annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.cloud.util.PropObj;

@Component
public class IOFile {

	/**
	 * 写入文件
	 * 
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String content) {
		File file = null;
		FileOutputStream fop = null;
		try {
			file = new File("/home/ftpuser_123/homePage/homePage.txt");

			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			fop = new FileOutputStream(file);

			byte[] contentInBytes = content.getBytes();
			fop.write(contentInBytes);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (null != fop) {
					fop.flush();
					fop.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}

	}

	/**
	 * 读取文件
	 * 
	 * @param content
	 * @return
	 */
	public static String readFile() {
		File file = null;
		BufferedReader br = null;
		StringBuilder result = new StringBuilder();
		try {
			file = new File("/home/ftpuser_123/homePage/homePage.txt");
			if (file.exists()) {
				br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					result.append(s);
				}
			} else {
				return null;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (null != br) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
		return result.toString();

	}

	public static String readProperty(String info) {
		synchronized (IOFile.class) {
			String value = null;
			FileInputStream pInStream = null;
			Properties p = null;
			try {
				File pFile = new File(PropObj.webchatConfigPath);
				pInStream = new FileInputStream(pFile);
				p = new Properties();
				p.load(pInStream);
				value = p.getProperty(info);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					pInStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.clear();
			}
			return value;
		}
	}

	public static void writeProperty(String key, String value) {
		File file = null;
		Properties prop = null;
		InputStream fis = null;
		OutputStream fos = null;
		try {
			file = new File(PropObj.webchatConfigPath);

			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			prop = new Properties();
			fis = new FileInputStream(PropObj.webchatConfigPath);
			prop.load(fis);
			fos = new FileOutputStream(PropObj.webchatConfigPath);
			prop.setProperty(key, value);
			prop.store(fos, "update");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭文件
				if (!fis.equals("close")) {
					fis.close();
				}
				fos.close();
				prop.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		// System.err.println(path);
	}
}
