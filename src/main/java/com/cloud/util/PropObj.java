package com.cloud.util;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @描述: 共通配置文件
 * @作者: dusai
 * @作成时间: 2018年10月12日 下午5:48:08
 */
@Component
public class PropObj implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String smsUri;

    public static String smsSpid;

    public static String smsSppassword;

    public static String smsDc;

    public static String webchatAppid;

    public static String webchatSecret;

    public static String webchatSecretUrl;

    public static String webchatToken;

    public static String webchatConfigPath;

    public static String webchatTemplateId;

    public static String domain;

    public static String aliOssEndpoint;

    public static String aliOssBacketName;

    public static String aliOssAccessKeyId;

    public static String aliOssAccessKeySecret;

    public static String aliOssFolder;
    
    public static String gpaiUrl;

   
    @Value("${gpai.url}")
	public static void setGpaiUrl(String gpaiUrl) {
		PropObj.gpaiUrl = gpaiUrl;
	}
    public static String getGpaiUrl() {
		return gpaiUrl;
	}
	public static String getSmsUri() {
        return smsUri;
    }

    @Value("${sms.uri}")
    public static void setSmsUri(String smsUri) {
        PropObj.smsUri = smsUri;
    }

    public static String getSmsSpid() {
        return smsSpid;
    }

    @Value("${sms.spid}")
    public static void setSmsSpid(String smsSpid) {
        PropObj.smsSpid = smsSpid;
    }

    public static String getSmsSppassword() {
        return smsSppassword;
    }

    @Value("${sms.sppassword}")
    public static void setSmsSppassword(String smsSppassword) {
        PropObj.smsSppassword = smsSppassword;
    }

    public static String getSmsDc() {
        return smsDc;
    }

    @Value("${sms.dc}")
    public static void setSmsDc(String smsDc) {
        PropObj.smsDc = smsDc;
    }

    public static String getWebchatAppid() {
        return webchatAppid;
    }

    @Value("${webchat.appid}")
    public static void setWebchatAppid(String webchatAppid) {
        PropObj.webchatAppid = webchatAppid;
    }

    public static String getWebchatSecret() {
        return webchatSecret;
    }

    @Value("${webchat.secret}")
    public static void setWebchatSecret(String webchatSecret) {
        PropObj.webchatSecret = webchatSecret;
    }

    public static String getWebchatSecretUrl() {
        return webchatSecretUrl;
    }

    @Value("${webchat.secret_url}")
    public static void setWebchatSecretUrl(String webchatSecretUrl) {
        PropObj.webchatSecretUrl = webchatSecretUrl;
    }

    public static String getWebchatToken() {
        return webchatToken;
    }

    @Value("${webchat.token}")
    public static void setWebchatToken(String webchatToken) {
        PropObj.webchatToken = webchatToken;
    }

    public static String getWebchatConfigPath() {
        return webchatConfigPath;
    }

    @Value("${webchat.configpath}")
    public static void setWebchatConfigPath(String webchatConfigPath) {
        PropObj.webchatConfigPath = webchatConfigPath;
    }

    public static String getWebchatTemplateId() {
        return webchatTemplateId;
    }

    @Value("${webchat.template_id}")
    public static void setWebchatTemplateId(String webchatTemplateId) {
        PropObj.webchatTemplateId = webchatTemplateId;
    }

    public static String getDomain() {
        return domain;
    }

    @Value("${domain}")
    public static void setDomain(String domain) {
        PropObj.domain = domain;
    }

    public static String getAliOssEndpoint() {
        return aliOssEndpoint;
    }

    @Value("${aliOss.ENDPOINT}")
    public static void setAliOssEndpoint(String aliOssEndpoint) {
        PropObj.aliOssEndpoint = aliOssEndpoint;
    }

    public static String getAliOssBacketName() {
        return aliOssBacketName;
    }

    @Value("${aliOss.BACKET_NAME}")
    public static void setAliOssBacketName(String aliOssBacketName) {
        PropObj.aliOssBacketName = aliOssBacketName;
    }

    public static String getAliOssAccessKeyId() {
        return aliOssAccessKeyId;
    }

    @Value("${aliOss.ACCESS_KEY_ID}")
    public static void setAliOssAccessKeyId(String aliOssAccessKeyId) {
        PropObj.aliOssAccessKeyId = aliOssAccessKeyId;
    }

    public static String getAliOssAccessKeySecret() {
        return aliOssAccessKeySecret;
    }

    @Value("${aliOss.ACCESS_KEY_SECRET}")
    public static void setAliOssAccessKeySecret(String aliOssAccessKeySecret) {
        PropObj.aliOssAccessKeySecret = aliOssAccessKeySecret;
    }

    public static String getAliOssFolder() {
        return aliOssFolder;
    }

    @Value("${aliOss.FOLDER}")
    public static void setAliOssFolder(String aliOssFolder) {
        PropObj.aliOssFolder = aliOssFolder;
    }

}