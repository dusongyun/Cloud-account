package com.cloud.account.vo;

import java.io.Serializable;
import java.util.Date;


public class UserAdminVo  implements Serializable {
	private static final long serialVersionUID = 8740063960887018514L;

	 private String id;

	    private String username;

	    private String password;

	    private String mobile;

	    private String smscode;

	    private Date smstime;

	    private String company;

	    private String nickname;

	    private String email;

	    private String address;

	    private Integer sex;

	    private String headPicUrl;

	    private String remark;

	    private Integer status;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getSmscode() {
			return smscode;
		}

		public void setSmscode(String smscode) {
			this.smscode = smscode;
		}

		public Date getSmstime() {
			return smstime;
		}

		public void setSmstime(Date smstime) {
			this.smstime = smstime;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
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
			this.headPicUrl = headPicUrl;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		@Override
        public String toString() {
	        return "UserVo [id=" + id + ", username=" + username + ", password=" + password
	                + ", mobile=" + mobile + ", smscode=" + smscode + ", smstime=" + smstime
	                + ", company=" + company + ", nickname=" + nickname + ", email=" + email
	                + ", address=" + address + ", sex=" + sex + ", headPicUrl=" + headPicUrl
	                + ", remark=" + remark + ", status=" + status + "]";
        }

}