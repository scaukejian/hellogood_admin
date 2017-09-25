
package com.hellogood.http.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hellogood.exception.BusinessException;
import com.hellogood.utils.RSAUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hellogood.constant.Code;
import com.hellogood.http.vo.ActionVO;
import com.hellogood.http.vo.EmployeeVO;
import com.hellogood.service.ActionService;
import com.hellogood.service.EmpRoleRelationService;
import com.hellogood.service.EmployeeService;
import com.hellogood.service.RoleAndActionService;
import com.hellogood.service.RoleService;

/**
 * 后台用户登陆
 * @author kejian
 */
@Controller
@RequestMapping("validate")
public class LoginController extends BaseController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmpRoleRelationService empRoleRelationService;

	@Autowired
	private RoleAndActionService roleAndActionService;

	@Autowired
	private ActionService actionService;

	@Autowired
	private RoleService roleService;

	/**
	 * 职工登录
	 * 
	 * @param vo
	 * @param code
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doLogin")
	public Map<String, Object> doLogin(EmployeeVO vo, String code,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 验证码
		String validateCode = (String) request.getSession().getAttribute("validateCode");
		if (validateCode == null) {
			map.put(ERROR_MSG, "验证码已经失效！请刷新后再试。");
			return map; 
		}
		// 检查验证码是否正确
		if (!validateCode.equalsIgnoreCase(code)) {
			map.put(ERROR_MSG, "验证码不正确！请重新输入。");
			return map; 
		}
		
		// 验证用户账号 密码是否正确
		EmployeeVO employeeVO = employeeService.getEmployeeByAccountAndPassword(vo);
		if(employeeVO == null){
			map.put(ERROR_MSG, "用户名或密码不正确！请重新输入。");
			return map; 
		}
		// 员工是否被禁用
		if(employeeVO.getStatus() == Code.EMPLOYEE_STATUS_INVALID.intValue()){
			map.put(ERROR_MSG, "您已经被管理员禁用，请与管理员联系。");
			return map;
		}
		
		// session 绑定员工
		request.getSession().setAttribute("employee", employeeVO);

		if (vo.isRmbUser()) { //如果记住密码
			//  添加cookie 自动登陆
			Cookie cookie = null;
			try {
				cookie = new Cookie("emp", RSAUtil.encrypt(vo.getAccount() + "," + vo.getPassword() + "," + getRemoteHost(request)));
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("加密失败");
			}
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 8);
			response.addCookie(cookie);
		}

		// 获取当前用户所拥有的角色id
		List<Long> list = empRoleRelationService.getRoleIds(employeeVO.getId());
		// 绑定角色
		request.getSession().setAttribute("roles", roleService.getAllByRoleIds(list));
		// 当前用户所拥有的菜单id
		List<Long> actionIds = null;
		if (list != null && list.size() > 0)
			actionIds = roleAndActionService.getActionsByRoleIds(list);
		List<ActionVO> actionVOs = null;
		// 当前用户拥有的一级菜单
		if (actionIds != null && actionIds.size() > 0)
			actionVOs = actionService.findAllByTypeAndActions("1", actionIds);
		request.getSession().setAttribute("actions", actionVOs);

		//  添加cookie 用户角色
		String roleCodes = empRoleRelationService.getRoleCodesByEmpId(getCurrentEmployeeId(request));
		if(roleCodes == null) roleCodes = "";
		Cookie roleCookie = new Cookie("roleCodes", roleCodes);
		roleCookie.setPath("/");
		roleCookie.setMaxAge(60 * 60 * 8);
		response.addCookie(roleCookie);

		try {
			Subject subject= SecurityUtils.getSubject();
			subject.login(new UsernamePasswordToken(employeeVO.getAccount(), employeeVO.getPassword()));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw new BusinessException("用户名或者密码错误");
		}
		map.put(DATA, employeeVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 菜单加载
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadInfo")
	public Map<String, Object> loadInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 加载菜单信息
		map.put("actions", actionService.findAll());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取职工的下级菜单
	 * 
	 * @param actionId
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadSon/{actionId}-{empId}.do")
	public Map<String, Object> loadSon(@PathVariable String actionId,
			@PathVariable String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 员工所 拥有的角色id
		List<Long> roleIds = empRoleRelationService.getRoleIds(Long
				.parseLong(empId));
		// 员工拥的菜单id
		List<Long> actions = roleAndActionService.getActionsByRoleIds(roleIds);
		// 获取员工的下一级菜单
		map.put(DATA_LIST,
				actionService.findAllByParentAndActions(
						Long.parseLong(actionId), actions));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/regout.do")
	public Map<String, Object> regout(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 删除session绑定值
		request.getSession().removeAttribute("employee");
		request.getSession().removeAttribute("actions");
		request.getSession().removeAttribute("roles");
		Subject subject= SecurityUtils.getSubject();
		subject.logout();
		map.put(STATUS, STATUS_SUCCESS);
		return map;

	}

	/**
	 * 修改密码
	 * 
	 * @param newpwd
	 * @param oldpwd
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modifyPwd/{newpwd}-{oldpwd}.do")
	public Map<String, Object> modifyPwd(@PathVariable String newpwd,
			@PathVariable String oldpwd, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		EmployeeVO employee = getCurrentEmployee(req);
		if (employee != null) {
			// 验证旧密码是否正确
			employee.setPassword(oldpwd);
			employee = employeeService
					.getEmployeeByAccountAndPassword(employee);
			if (employee != null) {
				// 设置新密码
				employee.setPassword(newpwd);
				employeeService.update(employee);
				map.put(STATUS, STATUS_SUCCESS);
			} else {
				map.put(ERROR_MSG, "原始密码不正确。");
			}

		} else {
			map.put(ERROR_MSG, "修改失败！请重新登陆。");

		}
		return map;

	}

	/**
	 * 获取导航
	 */
	@ResponseBody
	@RequestMapping("/getLocation.do")
	public Map<String, Object> getLocation(@RequestParam String url,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取导航
		String result = actionService.getLocation(url);
		session.setAttribute("location", result);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	@RequestMapping("/unauthorizedUrl.do")
	public String unauthorizedRole(){
		//未授权角色用户跳转到unauthorizedTip.jsp页面
		return "unauthorizedTip";
	}
}
