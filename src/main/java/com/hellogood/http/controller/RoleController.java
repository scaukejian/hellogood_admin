package com.hellogood.http.controller;

import com.hellogood.http.shiro.RefreshAuthDefinitionsService;
import com.hellogood.http.vo.RoleVO;
import com.hellogood.service.EmpRoleRelationService;
import com.hellogood.service.RoleAndActionService;
import com.hellogood.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kejian
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private EmpRoleRelationService empRoleRelationService;

	@Autowired
	private RoleAndActionService roleAndActionService;

	@Autowired
	private RefreshAuthDefinitionsService refreshAuthDefinitionsService;

	@ResponseBody
	@RequestMapping("/add.do")
	public Map<String, Object> add(RoleVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		roleService.add(vo);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	@ResponseBody
	@RequestMapping("/pageJson.do")
	public Map<String, Object> pageJson(RoleVO vo, @RequestParam int page,
                                        @RequestParam int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pageInfo = roleService.pageJson(vo, page, pageSize);
		map.put(DATA_LIST, pageInfo.getList());
		map.put(TOTAL, pageInfo.getTotal());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	@ResponseBody
	@RequestMapping("/delete/{ids}.do")
	public Map<String, Object> delete(@PathVariable String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] value = ids.split(",");
		Long[] idArray = new Long[value.length];
		int index = 0;
		for (String str : value) {
			idArray[index] = Long.parseLong(str);
			index++;
		}
		// 删除角色菜单关系
		roleAndActionService.deleteByRoleIds(idArray);
		// 删除角色用户关系
		empRoleRelationService.deleteByRoleIds(idArray);
		// 删除角色
		roleService.delete(idArray);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Map<String, Object> update(RoleVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		roleService.update(vo);
		map.put(STATUS, STATUS_SUCCESS);
		return map;

	}

	@ResponseBody
	@RequestMapping("/get/{id}.do")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DATA, roleService.get(id));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取员工的所有角色
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAll/{empId}.do")
	public Map<String, Object> getAll(@PathVariable String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 所有角色
		map.put(DATA_LIST, roleService.getAll());
		// 获取职工的所有角色id
		map.put("roleIds",empRoleRelationService.getRoleIds(Long.parseLong(empId)));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAll.do")
	public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allroleList", roleService.getAll());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 修改角色菜单关系
	 * 
	 * @param actionIds
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyAction/{actionIds}-{roleId}.do")
	public Map<String, Object> modifyAction(@PathVariable String actionIds,
			@PathVariable String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断是否有选择菜单
		if (StringUtils.isNotBlank(actionIds)) {
			String[] actionIdArr = actionIds.split(",");
			Long[] ids = new Long[actionIdArr.length];
			int index = 0;
			for (String actionId : actionIdArr) {
				ids[index] = Long.parseLong(actionId);
				index++;
			}
			// 删除已有角色菜单关系
			roleAndActionService.deleteByRoleId(Long.parseLong(roleId));
			// 添加新的角色菜单关系
			roleAndActionService.adds(ids, Long.parseLong(roleId));
		} else {// 没有选择菜单：删除角色下所有菜单
			roleAndActionService.deleteByRoleId(Long.parseLong(roleId));
		}

		refreshAuthDefinitionsService.reloadFilterChains(); //刷新权限
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}
}
