package com.hellogood.http.vo;

import com.hellogood.domain.EmpRoleRelationKey;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

public class EmpRoleRelationKeyVO {
	private String empId;
	private String roleId;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void vo2Domain(EmpRoleRelationKey domain){
		try {
			BeaUtils.copyProperties(domain, this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取用户角色Key关联信息失败！");
		}
	}
	public void domain2VO(EmpRoleRelationKey domain){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取用户角色Key关联信息失败！");
		}
	}
}
