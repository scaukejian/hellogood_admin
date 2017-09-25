package com.hellogood.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.Action;
import com.hellogood.domain.ActionExample;
import com.hellogood.http.vo.ActionVO;
import com.hellogood.http.vo.RoleVO;
import com.hellogood.mapper.ActionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author kejian
 */
@Service
@Transactional
public class ActionService {

	@Autowired
	private ActionMapper actionMapper;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleAndActionService roleAndActionService;
	
	@Autowired
	private EmpRoleRelationService empRoleRelationService;

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 */
	public Action add(ActionVO vo) {
		vo.setCreatetime(new Date());
		vo.setUpdatetime(new Date());
		Action action = new Action();
		vo.vo2Domain(action);
		actionMapper.insert(action);
		return action;
	}

	/**
	 * 菜单列表分页
	 * 
	 * @param vo
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PageInfo pageJson(ActionVO vo, int page, int pageSize) {
		// 分页
		PageHelper.startPage(page, pageSize);
		List<ActionVO> list = new ArrayList<ActionVO>();
		ActionExample example = new ActionExample();
		// 条件查询
		ActionExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(vo.getName())) {
			// 模糊查询
			criteria.andNameLike(MessageFormat.format("%{0}%", vo.getName()));
		}
		if (!"0".equals(vo.getType())) {
			// 菜单类型 （1 一级 2 二级...）
			criteria.andTypeEqualTo(vo.getType());
		}
		example.setOrderByClause("type");
		List<Action> actions = actionMapper.selectByExample(example);
		PageInfo pageInfo = new PageInfo(actions);
		for (Action action : actions) {
			ActionVO actionVO = new ActionVO();
			actionVO.domain2VO(action);
			// 获取角色名
			actionVO.setRole(getRoleName(action.getId()));
			list.add(actionVO);
		}
		pageInfo.getList().clear();
		pageInfo.getList().addAll(list);
		return pageInfo;
	}

	/**
	 * 查询角色名
	 * 
	 * @param actionId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<String> getRoleName(Long actionId) {
		// 获取角色id
		List<Long> roleIdList = roleAndActionService
				.getRoleIdsByActionId(actionId);
		if (roleIdList != null && roleIdList.size() > 0) {
			// 获取对应角色
			List<RoleVO> roleVOList = roleService.getAllByRoleIds(roleIdList);
			List<String> roleName = new ArrayList<String>();
			for (RoleVO roleVO : roleVOList) {
				roleName.add(roleVO.getName());
			}
			return roleName;
		}
		return null;
	}

	public void delete(Long[] idArray) {
		for (Long id : idArray) {
			delete(id);
		}
	}

	private void delete(Long id) {
		actionMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查询菜单
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ActionVO get(Long id) {
		Action action = actionMapper.selectByPrimaryKey(id);
		ActionVO actionVO = new ActionVO();
		actionVO.domain2VO(action);
		return actionVO;
	}

	public void update(ActionVO vo) {
		Action action = new Action();
		vo.vo2Domain(action);
		action.setUpdatetime(new Date());
		actionMapper.updateByPrimaryKeySelective(action);
	}

	/**
	 * 获取所有菜单信息
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ActionVO> findAll() {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andIdIsNotNull();
		example.setOrderByClause("type");
		List<Action> list = actionMapper.selectByExample(example);
		List<ActionVO> list1 = new ArrayList<ActionVO>();
		for (Action object : list) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(object);
			list1.add(vo);
		}
		return list1;
	}

	/**
	 * 获取上一级菜单
	 * 
	 * @param type
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ActionVO> findAllByType(String type) {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(type);
		List<Action> list = actionMapper.selectByExample(example);
		List<ActionVO> list1 = new ArrayList<ActionVO>();
		for (Action object : list) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(object);
			list1.add(vo);
		}
		return list1;
	}

	/**
	 * 通过类型和url获取对应菜单
	 * 
	 * @param type
	 * @param url
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ActionVO> findAllByTypeAndUrl(String type, String url) {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(type);
		criteria.andUrlEqualTo(url);
		example.setOrderByClause("type");
		List<Action> list = actionMapper.selectByExample(example);
		List<ActionVO> list1 = new ArrayList<ActionVO>();
		for (Action object : list) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(object);
			list1.add(vo);
		}
		return list1;
	}

	/**
	 * 指定类型下对应id的菜单
	 * 
	 * @param type
	 * @param actionIds
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ActionVO> findAllByTypeAndActions(String type, List actionIds) {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(type);// 类型（菜单级别）
		criteria.andIdIn(actionIds);// 菜单id集合
		example.setOrderByClause("seqnum");// 序号
		List<ActionVO> list = new ArrayList<ActionVO>();
		List<Action> actions = actionMapper.selectByExample(example);
		for (Action action : actions) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(action);
			list.add(vo);
		}
		return list;
	}

	/**
	 * 获取指定菜单的子菜单，并且该子菜单在指定的菜单集合中
	 * 
	 * @param parent
	 * @param actionIds
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<ActionVO> findAllByParentAndActions(long parent,
			List<Long> actionIds) {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andParentEqualTo(parent);
		criteria.andIdIn(actionIds);
		example.setOrderByClause("seqnum");
		List<ActionVO> actionVOs = new ArrayList<ActionVO>();
		List<Action> actions = actionMapper.selectByExample(example);
		for (Action action : actions) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(action);
			actionVOs.add(vo);
		}
		return actionVOs;
	}

	/**
	 * 获取所有拥有父菜单的菜单
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Map<Long, List<ActionVO>> findAllSonAction() {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		//父菜单id
		criteria.andParentNotEqualTo(0L);
		Map<Long, List<ActionVO>> map = new HashMap<Long, List<ActionVO>>();
		List<Action> actionList = actionMapper.selectByExample(example);
		for (Action action : actionList) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(action);
			// map中是否已经保存了该父菜单的子菜单集 ，如没有则新建
			List<ActionVO> list = map.get(vo.getParent());
			if (list == null) {
				list = new ArrayList<ActionVO>();
			}
			list.add(vo);
			map.put(vo.getParent(), list);
		}
		return map;
	}

	/**
	 * 导航
	 * 
	 * @param url
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public String getLocation(String url) {
		String result = "";
		// 获取本菜单名
		if (StringUtils.isNotBlank(url)) {
			ActionVO action = getActionByUrl(url);
			if (action != null) {
				result += "&nbsp;&nbsp;/&nbsp;&nbsp;" + action.getName();
				action = get(action.getParent());
				if (action != null) {
					result = action.getName() + result;
				}
			}
		}
		return result;
	}

	/**
	 * 通过url获取菜单 ；url必须有值
	 * 
	 * @param url
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ActionVO getActionByUrl(String url) {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andUrlEqualTo(url);
		List<Action> actionList = actionMapper.selectByExample(example);
		if (actionList != null && actionList.size() > 0) {
			ActionVO vo = new ActionVO();
			vo.domain2VO(actionList.get(0));
			return vo;
		}
		return null;
	}
	
	/**
	 * 自动登陆绑定菜单
	 * 
	 * @param type
	 * @param empId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Object getEmployeeOneActions(String type, Long empId) {
		List<Long> roleIds = empRoleRelationService.getRoleIds(empId);
		List<Long> actionIds = roleAndActionService.getActionsByRoleIds(roleIds);
		return findAllByTypeAndActions(type, actionIds);
	}
}
