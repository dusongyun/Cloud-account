package com.cloud.account.vo;

import java.io.Serializable;
import java.util.List;

public class UserQueryVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String openId;

	private String mobile;

	private String courtId;

	private String organId;

	private Integer type;

	private Integer userType;

	private List<Long> ids;

	private List<String> mobiles;
	
	private List<String> typeCodes;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCourtId() {
		return courtId;
	}

	public void setCourtId(String courtId) {
		this.courtId = courtId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public List<String> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}

	public List<String> getTypeCodes() {
		return typeCodes;
	}

	public void setTypeCodes(List<String> typeCodes) {
		this.typeCodes = typeCodes;
	}
	
	

}