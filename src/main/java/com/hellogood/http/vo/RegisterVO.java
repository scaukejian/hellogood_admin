package com.hellogood.http.vo;

public class RegisterVO {

	private String mobile;
	private String password;
	private String sex;
	private String smsCode;
	private String loginIp;
	private String loginAddr;
	private String clientType;
	private Integer inviteUid;// 邀请人
	private String inviteType;// 邀请类型
	private String functionType;// 功能类型 personal/activity
	private String sourceId;// 来源ID, 活动ID/用户ID

	public RegisterVO(String mobile, String password, String smsCode,
			String loginIp, String loginAddr, String clientType,
			Integer inviteUid, String inviteType) {
		this.mobile = mobile;
		this.password = password;
		this.smsCode = smsCode;
		this.loginIp = loginIp;
		this.loginAddr = loginAddr;
		this.clientType = clientType;
		this.inviteUid = inviteUid;
		this.inviteType = inviteType;
	}

	public RegisterVO(String mobile, String password, String smsCode) {
		super();
		this.mobile = mobile;
		this.password = password;
		this.smsCode = smsCode;
	}

	public RegisterVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginAddr() {
		return loginAddr;
	}

	public void setLoginAddr(String loginAddr) {
		this.loginAddr = loginAddr;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientType() {
		return clientType;
	}

	public Integer getInviteUid() {
		return inviteUid;
	}

	public void setInviteUid(Integer inviteUid) {
		this.inviteUid = inviteUid;
	}

	public String getInviteType() {
		return inviteType;
	}

	public void setInviteType(String inviteType) {
		this.inviteType = inviteType;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
