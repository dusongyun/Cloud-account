package com.cloud.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Image {
	public static void main(String[] args) {
		String imgStr = GetImageStr();
		System.out.println(imgStr);
		File file = new File("/Users/wangjian/Desktop/img-test.jpg");
		GenerateImage(imgStr,file);
	}

	/**
	 * //图片转化成base64字符串
	 * 
	 * @author 作者：wangjian Email：202109618@qq.com
	 * @version 创建时间：2018年5月29日 上午11:35:25
	 * @return
	 */
	public static String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "/Users/wangjian/Desktop/WechatIMG401.png";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 *  base64字符串转化成图片返回File
	 * @author 作者：wangjian  Email：202109618@qq.com
	 * @version 创建时间：2018年5月29日 上午11:35:33
	 * @param imgStr
	 * @return
	 */
	public static File GenerateImage(String imgStr,File uploadFile) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		BufferedInputStream bis = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			FileOutputStream out = new FileOutputStream(uploadFile);
			out.write(b);
			out.flush();
			out.close();
			return uploadFile;
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
