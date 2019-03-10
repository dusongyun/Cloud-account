package com.cloud.account.entity;

import java.io.Serializable;
import java.util.Date;

public class RecordTransfer implements Serializable {
    private String id;

    private String userCompanyid;

    private String userPayeeid;

    private String smsCodeid;

    private Long transferAmount;

    private String transferType;

    private String status;

    private Long createUser;

    private Date createTime;

    private Long updateUser;

    private Date updateTime;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserCompanyid() {
        return userCompanyid;
    }

    public void setUserCompanyid(String userCompanyid) {
        this.userCompanyid = userCompanyid == null ? null : userCompanyid.trim();
    }

    public String getUserPayeeid() {
        return userPayeeid;
    }

    public void setUserPayeeid(String userPayeeid) {
        this.userPayeeid = userPayeeid == null ? null : userPayeeid.trim();
    }

    public String getSmsCodeid() {
        return smsCodeid;
    }

    public void setSmsCodeid(String smsCodeid) {
        this.smsCodeid = smsCodeid == null ? null : smsCodeid.trim();
    }

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType == null ? null : transferType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}