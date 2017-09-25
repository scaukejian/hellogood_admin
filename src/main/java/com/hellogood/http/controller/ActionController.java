package com.hellogood.http.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hellogood.http.shiro.RefreshAuthDefinitionsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hellogood.domain.Action;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.ActionVO;
import com.hellogood.service.ActionService;
import com.hellogood.service.RoleAndActionService;
import com.hellogood.service.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * @author kejian
 */
@Controller
@RequestMapping("action")
public class ActionController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ActionController.class);

	@Autowired
	private ActionService actionService;

	@Autowired
	private RoleAndActionService roleAndActionService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RefreshAuthDefinitionsService refreshAuthDefinitionsService;

	/**
	 * 新增菜单
	 * 
	 * @param vo
	 * @param roleIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add.do")
	public Map<String, Object> add(ActionVO vo, @RequestParam String roleIds,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Action action = actionService.add(vo);
		logger.info(getCurrentEmployee(request).getName() + "新增菜单："
				+ vo.getName() + "  url:" + vo.getUrl());
		// 添加菜单角色关系
		if (StringUtils.isNotBlank(roleIds)) {
			Long[] roleIdArray = ids2LongArray(roleIds);
			roleAndActionService.addRoleRelations(roleIdArray, action.getId());// 添加菜单角色关系
		}
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 菜单列表
	 * 
	 * @param vo
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageJson.do")
	public Map<String, Object> pageJson(ActionVO vo, @RequestParam int page,
			@RequestParam int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pageInfo = actionService.pageJson(vo, page, pageSize);
		map.put(DATA_LIST, pageInfo.getList());
		map.put(STATUS, STATUS_SUCCESS);
		map.put(TOTAL, pageInfo.getTotal());
		return map;
	}

	/**
	 * 修改菜单
	 * 
	 * @param vo
	 * @param roleIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update.do")
	public Map<String, Object> update(ActionVO vo,
			@RequestParam String roleIds, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (vo != null) {
				// 删除已有菜单角色关系
				roleAndActionService.deleteByActionId(vo.getId());
			}
			if (StringUtils.isNotBlank(roleIds)) {
				String[] roleIdStr = roleIds.split(",");
				Long[] roleIdArr = new Long[roleIdStr.length];
				int index = 0;
				for (String roleId : roleIdStr) {
					roleIdArr[index] = Long.parseLong(roleId);
					index++;
				}
				// 添加菜单角色关系
				roleAndActionService.addRoleRelations(roleIdArr, vo.getId());
			}
			// 更新菜单
			actionService.update(vo);
			logger.info(getCurrentEmployee(request).getName() + "更新菜单："
					+ vo.getName() + "  url:" + vo.getUrl());

			refreshAuthDefinitionsService.reloadFilterChains(); //刷新权限
			map.put(STATUS, STATUS_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("菜单修改异常！");
		}
		return map;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete/{ids}.do")
	public Map<String, Object> delete(@PathVariable String ids,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] strs = ids.split(",");
		Long[] idArray = new Long[strs.length];
		int index = 0;
		for (String str : strs) {
			idArray[index] = Long.parseLong(str);
			index++;
		}
		// 删除菜单角色关联
		roleAndActionService.deleteByActionIds(idArray);
		// 删除菜单
		actionService.delete(idArray);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取菜单信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/{id}.do")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ActionVO vo = actionService.get(id);
		// 获取拥有此菜单的角色
		List<Long> roleIds = roleAndActionService.getRoleIdsByActionId(id);
		if (roleIds != null && roleIds.size() > 0) {
			map.put("roleList", roleService.getAllByRoleIds(roleIds));
		}
		// 获取所有角色
		map.put("allRoleList", roleService.getAll());
		map.put(DATA, vo);
		map.put(STATUS, STATUS_SUCCESS);
		// 获取上一级菜单
		map.put(DATA_LIST, actionService.findAllByTypeAndUrl(
				(vo != null && vo.getType() != null) ? (Long.parseLong(vo
						.getType()) - 1) + "" : "", ""));
		return map;
	}

	/**
	 * 根据角色id获取菜单信息
	 * 
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAll/{roleId}.do")
	public Map<String, Object> getAll(@PathVariable Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取一级菜单
		List<ActionVO> oneLevelList = actionService.findAllByType("1");
		// 获取所有子菜单
		Map<Long, List<ActionVO>> actionMap = actionService.findAllSonAction();
		map.put("allSonMap", actionMap);
		map.put(DATA_LIST, oneLevelList);
		// 获取角色下的所有菜单
		map.put("actionIds",
				roleAndActionService.getActionsByRoleId(roleId));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取控制菜单的角色
	 * 
	 * @param actionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllByActionId/{actionId}.do")
	public Map<String, Object> getAllByActionId(@PathVariable Long actionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, STATUS_SUCCESS);
		map.put(DATA_LIST, roleService.getAll());
		// 获取当前菜单下的所有角色id
		map.put("actionIds", roleAndActionService.getRoleIdsByActionId(actionId));
		return map;
	}

	/**
	 * 菜单赋权
	 * 
	 * @param roleIds
	 * @param actionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/permission/{roleIds}-{actionId}.do")
	public Map<String, Object> permission(@PathVariable String roleIds,
			@PathVariable Long actionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(roleIds)) {
			Long[] idArray =  ids2LongArray(roleIds);

			// 删除已有角色菜单关系
			roleAndActionService.deleteByActionId(actionId);
			// 添加现有角色菜单关系
			roleAndActionService.addRoleRelations(idArray,
					actionId);
		} else {
			// 去掉所有角色菜单关系
			roleAndActionService.deleteByActionId(actionId);
		}
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 类型-获取上级菜单
	 * 
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllByType/{type}.do")
	public Map<String, Object> getAllType(@PathVariable String type) {
		if (StringUtils.isNotBlank(type)) {
			type = (Integer.parseInt(type) - 1) + "";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取上级菜单
		map.put(DATA_LIST, actionService.findAllByTypeAndUrl(type, ""));
		return map;
	}
}
