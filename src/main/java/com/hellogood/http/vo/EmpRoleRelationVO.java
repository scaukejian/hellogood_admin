package com.hellogood.http.vo;

import com.hellogood.domain.EmpRoleRelation;
import com.hellogood.domain.Employee;
import com.hellogood.domain.Role;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

public class EmpRoleRelationVO {
	private Role role;
	private Employee employee;
	public Role getrole() {
		return role;
	}
	public void setrole(Role role) {
		this.role = role;
	}
	public Employee getemployee() {
		return employee;
	}
	public void setemployee(Employee employee) {
		this.employee = employee;
	}
	
	public void vo2Domain(EmpRoleRelation domain)
	{
		try {
			BeaUtils.copyProperties(domain, this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取员工角色关系信息失败！");
		}
	}
	public  void domain2VO(EmpRoleRelation domain){
		try {
			BeaUtils.copyProperties(this, domain);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取员工角色关系信息失败！");
		}
	}
}
