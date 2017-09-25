package com.hellogood.http.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hellogood.domain.Employee;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;
import com.hellogood.utils.PasswordDigester;

/**
 * @author kejian
 */
public class EmployeeVO implements Serializable {

	private static final long serialVersionUID = 3889383137451410629L;
	private Long id;
	private String name;
	private String account;
	private String password;
	private String position;// 职务
	private String email;
	private String mobilePhone;
	private String city;
	private Integer status;// 员工状态
	private String telephone;// 固定电话
	private String organizationId;
	private String nation;// 民族

    private Date createTime;

    private Date updateTime;

    private String userCode;
	private List<String> role;// 用户角色名
	private boolean rmbUser;

	public boolean isRmbUser() {
		return rmbUser;
	}

	public void setRmbUser(boolean rmbUser) {
		this.rmbUser = rmbUser;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	private Date updatetime;

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
        if (StringUtils.isNotBlank(password) && password.length() > 30) {
            this.password = password;
        } else {
            this.password = PasswordDigester.getPassMD5(account + password);
        }

    }

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public void vo2Domain(Employee domain) {
		try {
			BeaUtils.copyProperties(domain, this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取员工信息失败");
		}
	}

	public void domain2Vo(Employee domain) {
		try {
			BeaUtils.copyProperties(this, domain);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取员工信息失败");
		}
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Override
	public String toString() {
		return "EmployeeVO [id=" + id + ", name=" + name + ", account=" + account + ", password=" + password
				+ ", position=" + position + ", email=" + email + ", mobilePhone=" + mobilePhone + ", city=" + city
				+ ", status=" + status + ", telephone=" + telephone + ", organizationId=" + organizationId + ", nation="
				+ nation + ", createTime=" + createTime + ", updateTime=" + updateTime + ", userCode=" + userCode
				+ ", role=" + role + ", updatetime=" + updatetime + ", getRole()=" + getRole() + ", getUpdatetime()="
				+ getUpdatetime() + ", getId()=" + getId() + ", getName()=" + getName() + ", getAccount()="
				+ getAccount() + ", getPassword()=" + getPassword() + ", getPosition()=" + getPosition()
				+ ", getEmail()=" + getEmail() + ", getMobilePhone()=" + getMobilePhone() + ", getCity()=" + getCity()
				+ ", getStatus()=" + getStatus() + ", getTelephone()=" + getTelephone() + ", getOrganizationId()="
				+ getOrganizationId() + ", getNation()=" + getNation() + ", getCreateTime()=" + getCreateTime()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUserCode()=" + getUserCode() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
