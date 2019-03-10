package com.cloud.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RecordIncom implements Serializable {
    private Integer id;

    private String userCompanyid;

    private BigDecimal incomAmount;

    private BigDecimal actualAmount;

    private String incomType;

    private String status;

    private Long createUser;

    private Date createTime;

    private Long updateUser;

    private Date updateTime;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCompanyid() {
        return userCompanyid;
    }

    public void setUserCompanyid(String userCompanyid) {
        this.userCompanyid = userCompanyid == null ? null : userCompanyid.trim();
    }

    public BigDecimal getIncomAmount() {
        return incomAmount;
    }

    public void setIncomAmount(BigDecimal incomAmount) {
        this.incomAmount = incomAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getIncomType() {
        return incomType;
    }

    public void setIncomType(String incomType) {
        this.incomType = incomType == null ? null : incomType.trim();
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