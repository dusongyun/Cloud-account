package com.cloud.util.file;

import java.io.File;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.cloud.util.PropObj;

//阿里云API的内或外网域名   ENDPOINT
//阿里云API的bucket名称
//阿里云API的密钥Access Key ID  
//阿里云API的密钥Access Key Secret  
/**
 * @日期：2018年11月22日
 * @作者：Sai.Du
 * @描述：
 */
@Component
public class OSSUploadUtil2 {
    public static PropObj propObj;

    @SuppressWarnings("static-access")
    @Autowired
    public void setPropObj(PropObj propObj) {
        this.propObj = propObj;
    }

    private static Logger logger = Logger.getLogger(OSSUploadUtil2.class);

    /**
     * 带进度的上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "static-access", "deprecation", "unused" })
    public String uploadPicWithProgress(File file, HttpServletRequest request) throws Exception {
        String endpoint = propObj.aliOssEndpoint;
        String accessKeyId = propObj.aliOssAccessKeyId;
        String accessKeySecret = propObj.aliOssAccessKeySecret;
        String bucketName = propObj.aliOssBacketName;

        OSSClient oSSClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            /*
             * 这里用带进度条的OSS上传
             * 将session传入PutObjectProgressListener的构造中!官网例子是没有这个操作的 注意new
             * PutObjectRequest ()的第三个参数是File而不是官网介绍的FileInputStream,否则获取不到进度.
             */
            PutObjectResult putObjectResult = oSSClient.putObject(new PutObjectRequest(bucketName, propObj.aliOssFolder
                    + file.getName(), file).<PutObjectRequest> withProgressListener(new PutObjectProgressListener(
                    request.getSession())));
        } catch (OSSException oe) {
            logger.error(
                    "Caught an OSSException, which means your request made it to OSS, "
                            + "but was rejected with an error response for some reason.Error Message: "
                            + oe.getErrorCode() + "Error Code:" + oe.getErrorCode() + "Request ID:" + oe.getRequestId()
                            + "Host ID:" + oe.getHostId(), oe);
            throw new OSSException(oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.Error Message:" + ce.getMessage(), ce);
            throw new ClientException(ce);
        } finally {
            oSSClient.shutdown();
        }

        return getUrl(file.getName());
    }

    public static class PutObjectProgressListener implements ProgressListener {
        private HttpSession session;
        private long bytesWritten = 0;
        private long totalBytes = -1;
        private boolean succeed = false;
        private int percent = 0;

        // 构造方法中加入session
        public PutObjectProgressListener() {
        }

        public PutObjectProgressListener(HttpSession mSession) {
            this.session = mSession;
            session.setAttribute("upload_percent", percent);
        }

        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                logger.info("Start to upload......");
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                logger.info(this.totalBytes + " bytes in total will be uploaded to OSS");
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                this.bytesWritten += bytes;
                if (this.totalBytes != -1) {
                    percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
                    // 将进度percent放入session中
                    session.setAttribute("upload_percent", percent);
                    logger.info(bytes + " bytes have been written at this time, upload progress: " + percent + "%("
                            + this.bytesWritten + "/" + this.totalBytes + ")");
                } else {
                    logger.info(bytes + " bytes have been written at this time, upload ratio: unknown" + "("
                            + this.bytesWritten + "/...)");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                logger.info("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                break;
            case TRANSFER_FAILED_EVENT:
                logger.info("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                break;
            default:
                break;
            }
        }

        public boolean isSucceed() {
            return succeed;
        }
    }

    /**
     * 获得url链接
     * 
     * @param key
     * @return
     */
    @SuppressWarnings({ "static-access", "deprecation" })
    public String getUrl(String fileName) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = new OSSClient(propObj.aliOssEndpoint, propObj.aliOssAccessKeyId, propObj.aliOssAccessKeySecret)
                .generatePresignedUrl(propObj.aliOssBacketName, propObj.aliOssFolder + fileName, expiration);
        if (url != null) {
            String result = url.toString().replace("test50.oss-cn-shanghai.aliyuncs.com", "cdnimg.gpai.net");
            if (getContentType(fileName).startsWith("image"))
                result = result.substring(0, result.indexOf("?Expires")) + "?x-oss-process=image/resize,p_10";
            return result;
        }
        return "";
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * 
     * @param fileName
     *            文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".docx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        if (".xls".equalsIgnoreCase(fileExtension)) {
            return "application/x-xls";
        }
        if (".xlsx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (".pdf".equalsIgnoreCase(fileExtension)) {
            return "application/pdf";
        }
        if (".mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mp4";
        }
        if (".zip".equalsIgnoreCase(fileExtension)) {
            return "application/zip";
        }
        // 默认返回类型
        return "image/jpeg";
    }

    public Object getProgress(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int percent = session.getAttribute("upload_percent") == null ? 0 : (int) session.getAttribute("upload_percent");
        return percent;
    }

}
