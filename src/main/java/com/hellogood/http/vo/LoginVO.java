package com.hellogood.http.vo;

import java.util.Date;

import com.hellogood.domain.Login;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

/**
 * Created by kejian
 */
public class LoginVO {

    private Integer id;

    private Integer userId;

    private String phone;

    private Integer blacklist;

    private String phoneClient;

    private String password;

    private String loginIp;

    private String loginAddr;

    private Date createTime;

    private Date lastLogintime;

    private Date lastLogouttime;
    
    private String apkVersion;

    private String clientInfo;

    public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public String getPhoneClient() {
        return phoneClient;
    }

    public void setPhoneClient(String phoneClient) {
        this.phoneClient = phoneClient;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLogintime() {
        return lastLogintime;
    }

    public void setLastLogintime(Date lastLogintime) {
        this.lastLogintime = lastLogintime;
    }

    public Date getLastLogouttime() {
        return lastLogouttime;
    }

    public void setLastLogouttime(Date lastLogouttime) {
        this.lastLogouttime = lastLogouttime;
    }

    public void vo2Domain(Login domain){
        try {
            BeaUtils.copyProperties(domain, this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取登录信息失败");
        }
    }

    public void domain2Vo(Login domain){
        try {
            BeaUtils.copyProperties(this, domain);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取登录信息失败");
        }
    }
}
