package com.cloud.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类
 *
 * @author
 * @since 1.0
 */
public class FileUtil {


	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	// 图片上传的常量
	public static final String uploadFilePath = "/home/hbadmin/data";
	public static final String tempFile = "tempFile"; // 默认临时目录
	public static final String downFile = "downFile"; // 默认下载目录
	public static final String separator = "/";
	public static final SimpleDateFormat sfymd = new SimpleDateFormat("yyyy-MM-dd"); // 默认日子格式

	public static String getBasePath(HttpServletRequest request) {
		String appendString = request.getSession().getServletContext().getRealPath("");
		String basePath = (new StringBuilder()).append(appendString.replace("/WechatPlatfrom", "")).append(separator)
				.toString();

		return basePath;
	}

	public static String getTempPath() {
		String path = (new StringBuilder()).append(tempFile).append(separator).append(sfymd.format(new Date()))
				.append(separator).toString();
		return path;
	}

	public static String getDownPath() {
		String path = (new StringBuilder()).append(downFile).append(separator).append(sfymd.format(new Date()))
				.append(separator).toString();
		return path;
	}

	public static void copyAndDelete(String srcPath, String destPath) {
		if (StringUtils.isNotEmpty(srcPath) && StringUtils.isNotEmpty(destPath))
			copyFile(srcPath, destPath);
		deleteFile(srcPath);
	}

	/**
	 * 上传文件
	 * 
	 * @throws IOException
	 */
	public static File uploadFile(HttpServletRequest request, InputStream inputStream, String path, String newFileName)
			throws IOException {
		String basePath = (new StringBuilder()).append(getBasePath(request)).append(path).toString();
		String newFilePath = (new StringBuilder()).append(basePath).append(newFileName).toString();
		File file = FileUtil.createFile(newFilePath);
		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFilePath));
		StreamUtil.copyStream(inputStream, outputStream);
		return file;
	}

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @param pathFile
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String pathFile) {
		try {
			String newFilePath = (new StringBuilder()).append(getBasePath(request)).append(pathFile).toString();
			String fileName = pathFile.substring(pathFile.lastIndexOf(File.separator) + 1);
			String eng = request.getHeader("USER-AGENT");
			// 没有缓存
			// response.setDateHeader("Expires",new Date().getTime());
			// response.setHeader("Pragma", "no-cache");
			// response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", new Date().getTime() + 60 * 60 * 1000); // Expires:过时期限值+20秒
			response.setHeader("Cache-Control", "public"); // Cache-Control来控制页面的缓存与否,public:浏览器和缓存服务器都可以缓存页面信息；
			response.setHeader("Pragma", "Pragma"); // Pragma:设置页面是否缓存，为Pragma则缓存，no-cache则不缓存
			if (eng.indexOf("Safari") > -1 || eng.indexOf("Firefox") > -1 || eng.indexOf("Chrome") > -1) {
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				logger.info("FOR Mozilla kernel!");
			} else {
				response.setContentType("application/x-msdownload");
				response.addHeader("Content-Disposition",
						"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				logger.info("FOR IE MAXTHON kernel!");
			}
			File file = new File(newFilePath);
			response.addHeader("content-length", String.valueOf(file.length()));
			if (file.exists()) {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
				StreamUtil.copyStream(inputStream, outputStream);
			} else {
				logger.error("无此文件！" + pathFile);
			}
		} catch (Exception e) {
			logger.error("下载文件出错！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建目录
	 */
	public static File createDir(String dirPath) {
		File dir;
		try {
			dir = new File(dirPath);
			if (!dir.exists()) {
				FileUtils.forceMkdir(dir);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dir;
	}

	/**
	 * 创建文件
	 */
	public static File createFile(String filePath) {
		File file;
		try {
			file = new File(filePath);
			File parentDir = file.getParentFile();
			if (!parentDir.exists()) {
				FileUtils.forceMkdir(parentDir);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	/**
	 * 复制目录（不会复制空目录）
	 */
	public static void copyDir(String srcPath, String destPath) {
		try {
			File srcDir = new File(srcPath);
			File destDir = new File(destPath);
			if (srcDir.exists() && srcDir.isDirectory()) {
				FileUtils.copyDirectoryToDirectory(srcDir, destDir);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 复制文件
	 */
	public static void copyFile(String srcPath, String destPath) {
		try {
			File srcFile = new File(srcPath);
			File destFile = new File(destPath);
			if (srcFile.exists() && srcFile.isFile()) {
				FileUtils.copyFile(srcFile, destFile);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除目录
	 */
	public static void deleteDir(String dirPath) {
		try {
			File dir = new File(dirPath);
			if (dir.exists() && dir.isDirectory()) {
				FileUtils.deleteDirectory(dir);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除文件
	 */
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				FileUtils.forceDelete(file);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 重命名文件
	 */
	public static void renameFile(String srcPath, String destPath) {
		File srcFile = new File(srcPath);
		if (srcFile.exists()) {
			File newFile = new File(destPath);
			boolean result = srcFile.renameTo(newFile);
			if (!result) {
				throw new RuntimeException("重命名文件出错！" + newFile);
			}
		}
	}

	/**
	 * 将字符串写入文件
	 */
	public static void writeFile(String filePath, String fileContent) {
		OutputStream os = null;
		Writer w = null;
		try {
			FileUtil.createFile(filePath);
			os = new BufferedOutputStream(new FileOutputStream(filePath));
			w = new OutputStreamWriter(os, "UTF-8");
			w.write(fileContent);
			w.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (w != null) {
					w.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 获取真实文件名（去掉文件路径）
	 */
	public static String getRealFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean checkFileExists(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * 根据http地址获取图片解析
	 * 
	 * @author 作者：wangjian Email：202109618@qq.com
	 * @version 创建时间：2018年5月29日 下午3:12:54
	 * @param destUrl
	 */
	public static File convertSaveToFile(String destUrl,File uploadFile) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = -1;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			// 文件保存路径-默认图片保存格式为.jpg
			fos = new FileOutputStream(uploadFile);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
			System.err.println(uploadFile);
			return uploadFile;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return uploadFile;
	}

}
