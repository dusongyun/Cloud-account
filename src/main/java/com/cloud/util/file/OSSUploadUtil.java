package com.cloud.util.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.cloud.util.PropObj;
import com.cloud.util.RandomUtils;
import com.cloud.util.date.DateUtil;

//阿里云API的内或外网域名   ENDPOINT
//阿里云API的bucket名称
//阿里云API的密钥Access Key ID  
//阿里云API的密钥Access Key Secret  
@Component
public class OSSUploadUtil {
    public static PropObj propObj;

    @SuppressWarnings("static-access")
    @Autowired
    public void setPropObj(PropObj propObj) {
        this.propObj = propObj;
    }

    private static Logger logger = Logger.getLogger(OSSUploadUtil.class);

    // 默认5个线程
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    // 存储OSS返回来的partETags，是一个线程同步的arraylist，靠这个去查询
    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

    // 如果上传完毕，uploadId则失效，这里将client设为null，则进度查询会显示完毕（不是必须）
    private static OSSClient client = null;

    // 创建一个要存放的Object的名称，使用文件名怕重复，直接改名+存入数据库
    private static String key;

    // 文件地址
    private static String url;

    // 默认一个静态的字段贮备，查询进度需要
    private static String uploadId;

    // 默认一个静态的字段储备文件分割的数量，进度查询需要
    private static Integer partCount;

    // 标识符，false情况下说名还没有开始，true则前端可以开始查询进度（不是必须）
    private boolean progress = false;

    // 每片最少5MB，这里写死了，最好用配置文件随时修改
    final long partSize = 5 * 1024 * 1024L;

    /**
     * 获取阿里云OSS客户端对象
     * 
     * @return ossClient
     */
    @SuppressWarnings({ "static-access", "deprecation" })
    public OSSClient getOSSClient() {
        return new OSSClient(propObj.aliOssEndpoint, propObj.aliOssAccessKeyId, propObj.aliOssAccessKeySecret);
    }

    /**
     * 创建存储空间
     * 
     * @param ossClient
     *            OSS连接
     * @param bucketName
     *            存储空间
     * @return
     */
    public static String createBucketName(OSSClient ossClient, String bucketName) {
        // 存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            // 创建存储空间
            com.aliyun.oss.model.Bucket bucket = ossClient.createBucket(bucketName);
            logger.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * 
     * @param ossClient
     *            oss对象
     * @param bucketName
     *            存储空间
     */
    static void deleteBucket(OSSClient ossClient, String bucketName) {
        ossClient.deleteBucket(bucketName);
        logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     * 
     * @param ossClient
     *            oss连接
     * @param bucketName
     *            存储空间
     * @param folder
     *            模拟文件夹名如"qj_nanjing/"
     * @return 文件夹名
     */
    public static String createFolder(OSSClient ossClient, String bucketName, String folder) {
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            logger.info("创建文件夹成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * 
     * @param ossClient
     *            oss连接
     * @param bucketName
     *            存储空间
     * @param folder
     *            模拟文件夹名 如"qj_nanjing/"
     * @param key
     *            Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 上传文件File
     * 
     * @param ossClient
     *            oss连接
     * @param file
     *            上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName
     *            存储空间
     * @param folder
     *            模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * @throws IOException
     * */
    @SuppressWarnings("static-access")
    public String upload(File file) throws IOException {
        OSSClient ossClient = null;
        String resultStr = null;
        InputStream is = null;
        try {
            // 以输入流的形式上传文件
            is = new FileInputStream(file);
            // 文件名:源文件名称
            String fileName = file.getName();
            String fileType = "";
            if (fileName.lastIndexOf(".") > 0) {
                fileType = fileName.substring(fileName.lastIndexOf("."));
            }

            String targetFileName = createFileName() + fileType;
            // 文件大小
            Long fileSize = file.length();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传的文件的长度
            metadata.setContentLength(is.available());
            // 指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(targetFileName));
            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + targetFileName + "/" + fileSize + "Byte.");
            // 上传文件 (上传文件流的形式)
            ossClient = getOSSClient();
            ossClient.putObject(propObj.aliOssBacketName, propObj.aliOssFolder + targetFileName, is, metadata);
            // 解析结果
            // resultStr = putResult.getETag();
            resultStr = getUrl(targetFileName, file.length());
            ossClient.shutdown();
        } catch (IOException e) {
            is.close();
            e.printStackTrace();
        } catch (Exception e) {
            is.close();
            ossClient.shutdown();
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
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

    /**
     * 获得url链接
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("static-access")
    public String getUrl(String fileName, long size) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = getOSSClient().generatePresignedUrl(propObj.aliOssBacketName, propObj.aliOssFolder + fileName,
                expiration);
        if (url != null) {
            String result = url.toString().replace("test50.oss-cn-shanghai.aliyuncs.com", "cdnimg.gpai.net");
            if (getContentType(fileName).startsWith("image")) {
                if (size < 300 * 1024) {
                    result = result.substring(0, result.indexOf("?Expires")) + "?x-oss-process=image/resize,p_100";
                } else {
                    int scale = (int) (300 * 1024 * 100 / size);
                    if (scale < 1) {
                        scale = 1;
                    }
                    result = result.substring(0, result.indexOf("?Expires")) + "?x-oss-process=image/resize,p_" + scale;
                }
            }

            return result;
        }
        return "";
    }

    /**
     * 
     * <p>
     * 文件名称生成规则：年月日时分秒毫秒-UUID
     * </p>
     * 
     * @author dusai
     * @date 2017年4月13日 下午1:21:31
     * @return
     * @see
     */
    public String createFileName() {
        return DateUtil.dateToStr(DateUtil.getNow(), 12) + "-" + RandomUtils.getLowerString(4);
    }

    /**
     * @日期：2018年11月11日
     * @作者：Sai.Du
     * @参数：file 文件
     * @描述：分段上传图片
     */
    @SuppressWarnings("static-access")
    public String uploadOperation(File file) {
        try {

            client = getOSSClient();

            key = propObj.aliOssFolder + file.getName();
            // key = "ddddddd.mp4";
            url = getUrl(file.getName(), file.length());

            // 1-初始化一个分片上传事件、获得uploadId
            uploadId = getUploadId(client, propObj.aliOssBacketName, key);

            // 2-断点续传分片
            partCount = getPartCount(file);

            // 3-开始配置子线程，开始上传多片文件到自己的bucket（oss的文件夹）中
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (file.length() - startPos) : partSize;
                executorService.execute(new PartUploader(file, startPos, curPartSize, i + 1, uploadId));
            }

            // 4-方法执行中（只是步骤展示，这里没有代码）

            // 5-将标识符设为true，前端开始可以访问到上传进度
            progress = true;

            // 6-等待所有的线程上传完毕后关闭线程
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    // 如果有线程没有完毕，等待5秒
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 7-判断是否所有的分片都上传完毕（不是必须）
            // judgePartCount();

            // 8-必须：在将所有数据Part都上传完成后对整个文件的分片验证。
            verification();

            // 9-成功，恢复初始值（恢复初始值是为了不让这次上传影响其他的文件上传）
            restoreDefaultValues();

            // 10-下载上传的文件（不是必须）
            // client.getObject(new GetObjectRequest(bucketName, key), new
            // File(localFilePath));

            return url;

        } catch (OSSException oe) {
            System.out.println("OSS错误原因：");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("OSSClient错误原因：");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            // 注意关闭资源
            if (client != null) {
                client.shutdown();
            }
        }
        return null;

    }

    /**
     * 获得uploadId
     */
    public String getUploadId(OSSClient client, String bucketName, String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        ObjectMetadata metadata = new ObjectMetadata();
        // 指定该Object被下载时的网页的缓存行为
        metadata.setCacheControl("no-cache");
        // 指定该Object下设置Header
        metadata.setHeader("Pragma", "no-cache");
        // 指定该Object被下载时的内容编码格式
        metadata.setContentEncoding("utf-8");
        // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
        // 如果没有扩展名则填默认值application/octet-stream
        metadata.setContentType(getContentType(key));
        request.setObjectMetadata(metadata);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    /**
     * 断点续传分片数量判断
     */
    public int getPartCount(File file) {
        long fileLength = file.length();
        int partCount = (int) (fileLength / partSize); // 分片个数，不能超过10000
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("分片不能超过10000，请修改每片大小");
        } else {
            return partCount;
        }
    }

    /**
     * 子线程上传（多线程的上传效果提升明显，如果单线程则在for循环中一个个上传即可）
     */
    private static class PartUploader implements Runnable {
        private File localFile;
        private long startPos;
        private long partSize;
        private int partNumber;
        private String uploadId;

        public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId) {
            this.localFile = localFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
        }

        @SuppressWarnings("static-access")
        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = new FileInputStream(this.localFile);
                instream.skip(this.startPos);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(propObj.aliOssBacketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                synchronized (partETags) {
                    partETags.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 判断是否上传完毕，这里起始需要得到哪个错了，从哪个开始重新上传，获得part#，然后计算5M的大小上传 这里只是单纯的记录一下，以后再完善
     * 
     * @return
     */
    public void judgePartCount() {
        if (partETags.size() != partCount) {
            System.out.println("一部分没有上传成功，需要从ListMultipartUploads里面查看上传数量");
            throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
        } else {
            System.out.println("上传成功，文件名称：" + key + "\n");
        }
    }

    /**
     * 验证文件是否完全，OSS必须走这个方法 最后返回CompleteMultipartUploadResult，这里不继续深化了
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    public void verification() {
        // 修改顺序
        Collections.sort(partETags, new Comparator<PartETag>() {
            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });
        System.out.println("开始验证\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                propObj.aliOssBacketName, key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    /**
     * 恢复初始值
     * 
     * @return
     */
    public void restoreDefaultValues() {
        executorService = Executors.newFixedThreadPool(5);
        partETags = Collections.synchronizedList(new ArrayList<PartETag>());
        client = null;
        uploadId = null;
        partCount = 0;
    }

    /**
     * 页面上进度查询走这个方法
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    public Object getProgress() {
        // 获得OSS上面的已经好了的分片和总分片相除获得进度
        ListPartsRequest listPartsRequest = new ListPartsRequest(propObj.aliOssBacketName, key, uploadId);
        PartListing partListing;
        try {

            partListing = client.listParts(listPartsRequest);
            int nowPartCount = partListing.getParts().size();
            double db = (double) nowPartCount / partCount * 100;
            int rate = (int) db;
            System.out.println("nowPartCount=[" + nowPartCount + "];partCount=[" + partCount + "]db=[" + db
                    + "];rate=[" + rate);
            return rate;
        } catch (Exception e) {
            // 如果标识符为true说明已经开始上传，设为100%；否则说明刚进入方法，还没有上传，设为1%
            if (progress) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        int a = (int) ((double) 0 / 26 * 100);
        System.out.println("a===>>>" + a);
    }

    /**
     * @throws IOException
     * @日期：2018年11月16日
     * @作者：Sai.Du
     * @参数：path,文件地址
     * @描述：下载文件到本地
     */
    @SuppressWarnings("static-access")
    public void download(String path, HttpServletResponse response) throws IOException {

        int startIndex = path.lastIndexOf("/") + 1;
        int endIndex = path.lastIndexOf("?");
        // 开始位置、结束为止
        String fileName = path.substring(startIndex, endIndex);

        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>oss下载<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        OSSClient ossClient = getOSSClient();
        key = propObj.aliOssFolder + fileName;
        OSSObject object = ossClient.getObject(propObj.aliOssBacketName, key);
        BufferedInputStream input = new BufferedInputStream(object.getObjectContent());
        byte[] buffBytes = new byte[1024];

        OutputStream outputStream = response.getOutputStream();

        int read = 0;
        while ((read = input.read(buffBytes)) != -1) {
            outputStream.write(buffBytes, 0, read);
        }

        outputStream.flush();
        outputStream.close();
        input.close();
        ossClient.shutdown();

    }

}
