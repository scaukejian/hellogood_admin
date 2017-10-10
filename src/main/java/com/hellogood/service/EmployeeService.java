package com.hellogood.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.constant.Code;
import com.hellogood.constant.EhCacheCode;
import com.hellogood.domain.Employee;
import com.hellogood.domain.EmployeeExample;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.EmployeeVO;
import com.hellogood.http.vo.RoleVO;
import com.hellogood.mapper.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author kejian
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private EmpRoleRelationService empRoleRelationService;

	@Autowired
	private RoleService roleService;

	/**
	 * 获取员工信息
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(value = EhCacheCode.CACHE_TYPE_EMP, key = "#id")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public EmployeeVO selectByPrimaryKey(Long id) {
		Employee domain = employeeMapper.selectByPrimaryKey(id);
		EmployeeVO vo = new EmployeeVO();
		vo.domain2Vo(domain);
		return vo;
	}

	/**
	 * 通过账号 密码查询员工信息
	 * 
	 * @param employeeVO
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public EmployeeVO getEmployeeByAccountAndPassword(EmployeeVO employeeVO) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(employeeVO.getAccount()))
			criteria.andAccountEqualTo(employeeVO.getAccount());
		if (StringUtils.isNotBlank(employeeVO.getPassword())) {
			criteria.andPasswordEqualTo(employeeVO.getPassword());
		}
		criteria.andStatusNotEqualTo(Code.EMPLOYEE_STATUS_DEL);
		List<Employee> employees = employeeMapper.selectByExample(example);
		if (employees != null && employees.size() > 0) {
			EmployeeVO vo = new EmployeeVO();
			vo.domain2Vo(employees.get(0));
			return vo;
		}
		return null;
	}

	/**
	 * 添加员工
	 * 
	 * @param vo
	 */
	public EmployeeVO add(EmployeeVO vo) {
		Employee employee = new Employee();
		vo.vo2Domain(employee);
		employee.setCreateTime(new Date());
		employee.setUpdateTime(new Date());
		employee.setStatus(Code.EMPLOYEE_STATUS_VALID);
		employeeMapper.insert(employee);
		vo.setId(employee.getId());
		return vo;
	}

	/**
	 * 获取员工信息
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(value = EhCacheCode.CACHE_TYPE_EMP, key = "#id")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public EmployeeVO get(Long id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		EmployeeVO vo = new EmployeeVO();
		if (employee != null) {
			vo.domain2Vo(employee);
			vo.setRole(setRoleNameList(employee));
		}
		return vo;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public EmployeeVO getNoCache(Long id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		EmployeeVO vo = new EmployeeVO();
		if (employee != null) {
			vo.domain2Vo(employee);
			vo.setRole(setRoleNameList(employee));
		}
		return vo;
	}

	/**
	 * 批量删除员工
	 * 
	 * @param idLong
	 */

	public void delete(Long[] idLong) {
		for (Long id : idLong) {
			EmployeeVO vo=get(id);
			if(vo==null){
				throw new BusinessException("获取员工信息异常");
			}
			vo.setStatus(Code.EMPLOYEE_STATUS_DEL.intValue());
			update(vo, false);
		}
	}

	@CacheEvict(value = EhCacheCode.CACHE_TYPE_EMP, key = "#id")
	public void delete(Long id) {
		employeeMapper.deleteByPrimaryKey(id);

	}

	/**
	 * 更新员工信息
	 * 
	 * @param vo
	 */
	@Caching(
			evict = {@CacheEvict(value = EhCacheCode.CACHE_TYPE_EMP, key = "#vo.id")},
			put = {@CachePut(value = EhCacheCode.CACHE_TYPE_EMP, key = "#vo.id")}
	)
	public EmployeeVO update(EmployeeVO vo) {
		Employee employee = new Employee();
		vo.vo2Domain(employee);
		employee.setUpdateTime(new Date());
		employeeMapper.updateByPrimaryKey(employee);
		return vo;
	}
	
	/**
	 * 更新员工信息2
	 * 检查账号合法性
	 * @param vo
	 */
	@Caching(
			evict = {@CacheEvict(value = EhCacheCode.CACHE_TYPE_EMP, key = "#vo.id")},
			put = {@CachePut(value = EhCacheCode.CACHE_TYPE_EMP, key = "#vo.id")}
			)
	public EmployeeVO update(EmployeeVO vo, Boolean check) {
		return update(vo);
	}

	/**
	 * 查询分页
	 * 
	 * @param vo
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PageInfo pageJson(EmployeeVO vo, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		EmployeeExample example = new EmployeeExample();
		// 条件查询
		EmployeeExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			// 模糊查询
			criteria.andNameLike(MessageFormat.format("%{0}%", vo.getName()));
		}
		if (StringUtils.isNotBlank(vo.getUserCode())) {
			// 模糊查询
			criteria.andUserCodeLike(MessageFormat.format("%{0}%", vo.getUserCode()));
		}
		criteria.andStatusNotEqualTo(Code.EMPLOYEE_STATUS_DEL);
		List<Employee> employees = employeeMapper.selectByExample(example);

		PageInfo pageInfo = new PageInfo(employees);
		for (Employee employee : employees) {
			EmployeeVO employeeVO = new EmployeeVO();
			employeeVO.domain2Vo(employee);
			employeeVO.setRole(setRoleNameList(employee));
			list.add(employeeVO);
		}
		pageInfo.getList().clear();
		pageInfo.getList().addAll(list);
		return pageInfo;
	}

	private List<String>  setRoleNameList(Employee employee){
		List<String> rolenameList = new ArrayList<String>();
		List<Long> roleIdList = empRoleRelationService.getRoleIds(employee.getId());
		if (roleIdList != null && roleIdList.size() > 0) {
			List<RoleVO> roleList = roleService.getAllByRoleIds(roleIdList);
			for (RoleVO roleVO : roleList) {
				rolenameList.add(roleVO.getName());
			}
		}
		return rolenameList;
	}
	/**
	 * 获取所有可用员工信息
	 * 
	 * @return
	 */
	public List<EmployeeVO> findAll() {
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		EmployeeExample example = new EmployeeExample();
		// 条件查询
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(Code.EMPLOYEE_STATUS_VALID);// 可用状态
		List<Employee> employees = employeeMapper.selectByExample(example);
		for (Employee employee : employees) {
			EmployeeVO employeeVO = new EmployeeVO();
			employeeVO.domain2Vo(employee);
			list.add(employeeVO);
		}
		return list;
	}

	/**
	 * 查询员工
	 * 
	 * @param empIds
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<EmployeeVO> getByIds(List<Long> empIds) {
		if (empIds == null || empIds.size() == 0) {
			return null;
		}
		EmployeeExample example = new EmployeeExample();
		example.createCriteria().andIdIn(empIds);
		List<Employee> employeeList = employeeMapper.selectByExample(example);
		List<EmployeeVO> employeeVOs = new ArrayList<EmployeeVO>();
		for (Employee employee : employeeList) {
			EmployeeVO employeeVO = new EmployeeVO();
			employeeVO.domain2Vo(employee);
			employeeVOs.add(employeeVO);
		}
		return employeeVOs;
	}

	/**
	 * 模糊查询 通过姓名
	 * 
	 * @param name
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Integer> getEmployeeIdByName(String name) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria=example.createCriteria();
		criteria.andStatusNotEqualTo(Code.EMPLOYEE_STATUS_DEL);
		// 模糊查询
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike(MessageFormat.format("%{0}%", name));
		}
		List<Employee> employees = employeeMapper.selectByExample(example);
		List<Integer> list = new ArrayList<Integer>();
		for (Employee employee : employees) {
			list.add(employee.getId().intValue());
		}
		return list;
	}

	/**
	 * 模糊查询 通过姓名
	 * 
	 * @param name
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Long> getIdsByName(String name) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria=example.createCriteria();
		criteria.andStatusNotEqualTo(Code.EMPLOYEE_STATUS_DEL);
		// 模糊查询
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike(MessageFormat.format("%{0}%", name));
		}
		List<Employee> employees = employeeMapper.selectByExample(example);
		List<Long> list = new ArrayList<Long>();
		for (Employee employee : employees) {
			list.add(employee.getId());
		}
		return list;
	}
	/**
	 * 查询员工
	 * 
	 * @param name
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<EmployeeVO> getByName(String name) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria=example.createCriteria();
		criteria.andStatusNotEqualTo(Code.EMPLOYEE_STATUS_DEL);
		// 模糊查询
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike(MessageFormat.format("%{0}%", name));
		}
		List<Employee> employees = employeeMapper.selectByExample(example);
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		for (Employee employee : employees) {
			EmployeeVO vo = new EmployeeVO();
			vo.domain2Vo(employee);
			list.add(vo);
		}
		return list;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED , readOnly = true)
	public EmployeeVO autoLogin(String account, String password) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(account).andPasswordEqualTo(password)
				.andStatusNotEqualTo(Code.EMPLOYEE_STATUS_DEL);
		List<Employee> employees = employeeMapper.selectByExample(example);
		if (employees != null && employees.size() > 0) {
			EmployeeVO vo = new EmployeeVO();
			vo.domain2Vo(employees.get(0));
			return vo;
		}
		return null;
	}
}
