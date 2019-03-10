package com.cloud.account.vo;

import java.io.Serializable;
import java.util.Date;


public class UserCompanyVo  implements Serializable {

    private String id;

    private String username;

    private String password;

    private String mobile;

    private String smscode;

    private Date smstime;

    private String companyName;

    private String companyCode;

    private String corporationName;

    private String corporationIdno;

    private String corporationPhone;

    private String corporationPic1;

    private String corporationPic2;

    private String corporationPic3;

    private String managerName;

    private String managerIdno;

    private String managerPhone;

    private String managerPic1;

    private String managerPic2;

    private String managerPic3;

    private String email;

    private String address;

    private Integer sex;

    private String headPicUrl;

    private String remark;

    private Integer status;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode == null ? null : smscode.trim();
    }

    public Date getSmstime() {
        return smstime;
    }

    public void setSmstime(Date smstime) {
        this.smstime = smstime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName == null ? null : corporationName.trim();
    }

    public String getCorporationIdno() {
        return corporationIdno;
    }

    public void setCorporationIdno(String corporationIdno) {
        this.corporationIdno = corporationIdno == null ? null : corporationIdno.trim();
    }

    public String getCorporationPhone() {
        return corporationPhone;
    }

    public void setCorporationPhone(String corporationPhone) {
        this.corporationPhone = corporationPhone == null ? null : corporationPhone.trim();
    }

    public String getCorporationPic1() {
        return corporationPic1;
    }

    public void setCorporationPic1(String corporationPic1) {
        this.corporationPic1 = corporationPic1 == null ? null : corporationPic1.trim();
    }

    public String getCorporationPic2() {
        return corporationPic2;
    }

    public void setCorporationPic2(String corporationPic2) {
        this.corporationPic2 = corporationPic2 == null ? null : corporationPic2.trim();
    }

    public String getCorporationPic3() {
        return corporationPic3;
    }

    public void setCorporationPic3(String corporationPic3) {
        this.corporationPic3 = corporationPic3 == null ? null : corporationPic3.trim();
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getManagerIdno() {
        return managerIdno;
    }

    public void setManagerIdno(String managerIdno) {
        this.managerIdno = managerIdno == null ? null : managerIdno.trim();
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone == null ? null : managerPhone.trim();
    }

    public String getManagerPic1() {
        return managerPic1;
    }

    public void setManagerPic1(String managerPic1) {
        this.managerPic1 = managerPic1 == null ? null : managerPic1.trim();
    }

    public String getManagerPic2() {
        return managerPic2;
    }

    public void setManagerPic2(String managerPic2) {
        this.managerPic2 = managerPic2 == null ? null : managerPic2.trim();
    }

    public String getManagerPic3() {
        return managerPic3;
    }

    public void setManagerPic3(String managerPic3) {
        this.managerPic3 = managerPic3 == null ? null : managerPic3.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl == null ? null : headPicUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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