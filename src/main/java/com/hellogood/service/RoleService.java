package com.hellogood.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hellogood.mapper.RoleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.Role;
import com.hellogood.domain.RoleExample;
import com.hellogood.http.vo.RoleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author kejian
 */
@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public void add(RoleVO vo) {
		Role role = new Role();
		vo.vo2Domain(role);
		role.setCreatetime(new Date());
		role.setUpdatetime(new Date());
		roleMapper.insert(role);
		vo.setId(role.getId());
	}

	/**
	 * 分页
	 * 
	 * @param vo
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PageInfo pageJson(RoleVO vo, int page, int pageSize) {
		// 分页
		PageHelper.startPage(page, pageSize);
		List<RoleVO> list = new ArrayList<RoleVO>();
		RoleExample example = new RoleExample();
		// 条件查询
		RoleExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			// 模糊查询
			criteria.andNameLike(MessageFormat.format("%{0}%", vo.getName()));
		}
		List<Role> roles = roleMapper.selectByExample(example);
		PageInfo pageInfo = new PageInfo(roles);
		for (Role role : roles) {
			RoleVO roleVO = new RoleVO();
			roleVO.domain2VO(role);
			list.add(roleVO);
		}
		pageInfo.getList().clear();
		pageInfo.getList().addAll(list);
		return pageInfo;
	}

	public void delete(Long[] idArray) {
		for (Long id : idArray) {
			delete(id);
		}
	}

	private void delete(Long id) {

		roleMapper.deleteByPrimaryKey(id);
	}

	public void update(RoleVO vo) {
		Role domain = new Role();
		vo.vo2Domain(domain);
		domain.setUpdatetime(new Date());
		roleMapper.updateByPrimaryKeySelective(domain);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public RoleVO get(Long id) {
		Role role = roleMapper.selectByPrimaryKey(id);
		RoleVO vo = new RoleVO();
		vo.domain2VO(role);
		return vo;
	}

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List getAll() {
		List<RoleVO> list = new ArrayList<RoleVO>();
		RoleExample example = new RoleExample();
		// 条件查询
		RoleExample.Criteria criteria = example.createCriteria();
		criteria.andIdIsNotNull();
		List<Role> roles = roleMapper.selectByExample(example);
		for (Role role : roles) {
			RoleVO roleVO = new RoleVO();
			roleVO.domain2VO(role);
			list.add(roleVO);
		}
		return list;
	}

	/**
	 * 查询角色
	 * 
	 * @param list
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<RoleVO> getAllByRoleIds(List<Long> list) {
		List<RoleVO> roleVOs = null;
		RoleExample example = new RoleExample();
		RoleExample.Criteria criteria = example.createCriteria();
		if (list != null && list.size() > 0) {
			roleVOs = new ArrayList<RoleVO>();
			criteria.andIdIn(list);
			List<Role> roles = roleMapper.selectByExample(example);
			for (Role role : roles) {
				RoleVO vo = new RoleVO();
				vo.domain2VO(role);
				roleVOs.add(vo);
			}
		}
		return roleVOs;
	}

	/**
	 * 查询角色
	 * 
	 * @param roleName
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Role getByName(String roleName) {
		if (StringUtils.isBlank(roleName)) {
			return null;
		}
		RoleExample example = new RoleExample();
		example.createCriteria().andNameEqualTo(roleName);
		List<Role> roleList = roleMapper.selectByExample(example);
		if (roleList != null && roleList.size() > 0) {
			return roleList.get(0);
		}
		return null;
	}

}
