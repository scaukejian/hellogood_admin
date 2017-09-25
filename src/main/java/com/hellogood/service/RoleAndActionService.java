package com.hellogood.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.RoleActionRelationExample;
import com.hellogood.domain.RoleActionRelationKey;
import com.hellogood.mapper.RoleActionRelationMapper;

/**
 * @author kejian
 */
@Service
@Transactional
public class RoleAndActionService {

	@Autowired
	private RoleActionRelationMapper roleActionRelationMapper;

	/**
	 * 查询菜单id
	 * 
	 * @param roleId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Long> getActionsByRoleId(Long roleId) {
		RoleActionRelationExample example = new RoleActionRelationExample();
		List<Long> list = new ArrayList<Long>();
		RoleActionRelationExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		List<RoleActionRelationKey> keys = roleActionRelationMapper
				.selectByExample(example);
		for (RoleActionRelationKey key : keys) {
			list.add(key.getActionId());
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	/**
	 * 删除角色下的id
	 * 
	 * @param roleId
	 */
	public void deleteByRoleId(Long roleId) {
		RoleActionRelationExample example = new RoleActionRelationExample();
		RoleActionRelationExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		roleActionRelationMapper.deleteByExample(example);
	}

	/**
	 * 批量添加
	 * 
	 * @param ids
	 * @param roleId
	 */
	public void adds(Long[] ids, long roleId) {
		for (Long actionId : ids) {
			RoleActionRelationKey key = new RoleActionRelationKey();
			key.setActionId(actionId);
			key.setRoleId(roleId);
			insert(key);
		}
	}

	public void insert(RoleActionRelationKey key) {
		roleActionRelationMapper.insert(key);
	}

	public void deleteByActionId(Long actionId) {
		RoleActionRelationExample example = new RoleActionRelationExample();
		RoleActionRelationExample.Criteria criteria = example.createCriteria();
		criteria.andActionIdEqualTo(actionId);
		roleActionRelationMapper.deleteByExample(example);
	}

	public void addRoleRelations(Long[] roleIds, Long actionId) {
		for (Long roleId : roleIds) {
			RoleActionRelationKey key = new RoleActionRelationKey();
			key.setActionId(actionId);
			key.setRoleId(roleId);
			insert(key);
		}
	}

	/**
	 * 查询对应菜单的控制角色
	 * 
	 * @param actionId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Long> getRoleIdsByActionId(Long actionId) {
		RoleActionRelationExample example = new RoleActionRelationExample();
		RoleActionRelationExample.Criteria criteria = example.createCriteria();
		criteria.andActionIdEqualTo(actionId);
		List<RoleActionRelationKey> keys = roleActionRelationMapper
				.selectByExample(example);
		List<Long> list = new ArrayList<Long>();
		for (RoleActionRelationKey key : keys) {
			list.add(key.getRoleId());
		}
		return list;
	}

	public void deleteByRoleIds(Long[] idArray) {
		for (Long roleId : idArray) {
			deleteByRoleId(roleId);
		}

	}

	public void deleteByActionIds(Long[] idArray) {
		for (Long actionId : idArray) {
			deleteByActionId(actionId);
		}

	}

	/**
	 * 查询菜单id
	 * 
	 * @param roleIdArray
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Set getActionsByRoleIds(Long[] roleIdArray) {
		Set set = new HashSet();
		for (Long roleId : roleIdArray) {
			List list = getActionsByRoleId(roleId);
			for (Object object : list) {
				String id = (Long) object + "";
				set.add(id);
			}
		}
		return set;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Set<String> getActionsByRoleIdList(List<Long> roleIdList) {
		Set<String> set = new HashSet<String>();
		for (Long roleId : roleIdList) {
			List list = getActionsByRoleId(roleId);
			for (Object object : list) {
				String id = (Long) object + "";
				set.add(id);
			}
		}
		return set;
	}

	/**
	 * 查询菜单id
	 * 
	 * @param list
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Long> getActionsByRoleIds(List<Long> list) {
		RoleActionRelationExample example = new RoleActionRelationExample();
		RoleActionRelationExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdIn(list);
		List<RoleActionRelationKey> keys = roleActionRelationMapper
				.selectByExample(example);
		List<Long> actions = new ArrayList<>();
		for (RoleActionRelationKey roleActionRelationKey : keys) {
			actions.add(roleActionRelationKey.getActionId());
		}
		return actions;
	}

	/**
	 * 获取数据库所有角色-资源权限配置
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<RoleActionRelationKey> getAllRoleAndActions() {
		RoleActionRelationExample example = new RoleActionRelationExample();
		return roleActionRelationMapper.selectByExample(example);
	}
}
