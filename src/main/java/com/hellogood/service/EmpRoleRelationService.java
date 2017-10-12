package com.hellogood.service;

import java.util.ArrayList;
import java.util.List;

import com.hellogood.domain.Role;
import com.hellogood.http.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.EmpRoleRelationExample;
import com.hellogood.domain.EmpRoleRelationKey;
import com.hellogood.http.vo.EmpRoleRelationKeyVO;
import com.hellogood.mapper.EmpRoleRelationMapper;

/**
 * @author kejian
 */
@Service
@Transactional
public class EmpRoleRelationService {

	@Autowired
	private EmpRoleRelationMapper empRoleRelationMapper;

	@Autowired
	private RoleService roleService;

	/**
	 * 新增员工角色
	 * 
	 * @param vO
	 */
	public void add(EmpRoleRelationKeyVO vO) {
		EmpRoleRelationKey domain = new EmpRoleRelationKey();
		vO.vo2Domain(domain);
		addKey(domain);
	}

	/**
	 * 批量新增员工关系
	 * 
	 * @param empId
	 * @param idArray
	 */
	public void adds(Long empId, Long[] idArray) {
		for (Long roleId : idArray) {
			EmpRoleRelationKey domain = new EmpRoleRelationKey();
			domain.setEmpId(empId);
			domain.setRoleId(roleId);
			addKey(domain);
		}

	}

	private void addKey(EmpRoleRelationKey domain) {
		empRoleRelationMapper.insert(domain);
	}

	/**
	 * 获取角色id集
	 * 
	 * @param empId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Long> getRoleIds(Long empId) {
		List<Long> list = new ArrayList();
		EmpRoleRelationExample example = new EmpRoleRelationExample();
		EmpRoleRelationExample.Criteria criteria = example.createCriteria();
		criteria.andEmpIdEqualTo(empId);
		List<EmpRoleRelationKey> ralations = empRoleRelationMapper
				.selectByExample(example);
		for (EmpRoleRelationKey object : ralations) {
			list.add(object.getRoleId());
		}
		return list;
	}

	/**
	 * 删除员工角色
	 * 
	 * @param empId
	 */
	public void deleteByEmpId(Long empId) {
		EmpRoleRelationExample example = new EmpRoleRelationExample();
		EmpRoleRelationExample.Criteria criteria = example.createCriteria();
		criteria.andEmpIdEqualTo(empId);
		empRoleRelationMapper.deleteByExample(example);
	}

	/**
	 * 批量删除员工角色
	 * 
	 * @param idLong
	 */
	public void deleteByEmpIds(Long[] idLong) {
		for (Long id : idLong) {
			deleteByEmpId(id);
		}

	}

	/**
	 * 删除指定角色下的所有员工角色关系
	 * 
	 * @param idArray
	 */
	public void deleteByRoleIds(Long[] idArray) {
		for (Long roleId : idArray) {
			deleteByRoleId(roleId);
		}
	}

	private void deleteByRoleId(Long roleId) {
		EmpRoleRelationExample example = new EmpRoleRelationExample();
		EmpRoleRelationExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		empRoleRelationMapper.deleteByExample(example);
	}

	/**
	 * 查询员工id
	 * 
	 * @param id
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Long> getEmpIdsByRoleId(Long id) {
		if(id==null){
			return null;
		}
		EmpRoleRelationExample example=new EmpRoleRelationExample();
		example.createCriteria().andRoleIdEqualTo(id);
		List<EmpRoleRelationKey> errList=empRoleRelationMapper.selectByExample(example);
		List<Long> empIds=new ArrayList<Long>();
		
		for (EmpRoleRelationKey empRoleRelationKey : errList) {
			empIds.add(empRoleRelationKey.getEmpId());
		}
		return empIds;
	}

	public List<String> getRoleCodeListByEmpId(Long empId){
		EmpRoleRelationExample example = new EmpRoleRelationExample();
		example.createCriteria().andEmpIdEqualTo(empId);
		List<EmpRoleRelationKey> roleRelationList = empRoleRelationMapper.selectByExample(example);
		List<String> list = new ArrayList<>(roleRelationList.size());
		for (EmpRoleRelationKey empRoleRelation: roleRelationList) {
			RoleVO role = roleService.get(empRoleRelation.getRoleId());
			if(role != null) list.add(role.getCode());
		}
		return list;
	}

	public String getRoleCodesByEmpId(Long empId){
		List<String> list = getRoleCodeListByEmpId(empId);
		StringBuffer codes = new StringBuffer();
		for (String code: list){
			codes.append(code).append("#");//这里不能出现逗号，否则JDK8后会解析出错
		}
		return codes.length() > 0 ? codes.substring(0, codes.length() - 1).toString() : codes.toString();
	}
}
