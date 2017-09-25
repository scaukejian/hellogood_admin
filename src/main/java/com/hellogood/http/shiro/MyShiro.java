package com.hellogood.http.shiro;

import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.EmployeeVO;
import com.hellogood.service.EmpRoleRelationService;
import com.hellogood.service.EmployeeService;
import com.hellogood.service.RoleAndActionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author KJ
 */
@Service
@Transactional
public class MyShiro extends AuthorizingRealm {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmpRoleRelationService empRoleRelationService;
    @Autowired
    private RoleAndActionService roleAndActionService;

    /**
     * 登录认证（获取认证信息，只在用户第一次登录时执行）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 查出是否有此用户
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setAccount(token.getUsername());
        employeeVO = employeeService.getEmployeeByAccountAndPassword(employeeVO);
        if (employeeVO == null) new BusinessException("用户账号不存在");
        // 若存在，将此用户存放到登录认证info中
        return new SimpleAuthenticationInfo(employeeVO.getAccount(), employeeVO.getPassword(), getName());
    }

    /**
     * 权限认证(获取授权信息，只在用户第一次访问资源配置文件（指的是动态url权限）时执行)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录时输入的用户名
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
        // 到数据库查是否有此对象
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setAccount(loginName);
        employeeVO = employeeService.getEmployeeByAccountAndPassword(employeeVO);
        if (employeeVO == null) new BusinessException("用户账号不存在");
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 用户的角色集合名字
        List<String> roleCodeList = empRoleRelationService.getRoleCodeListByEmpId(employeeVO.getId());
        info.setRoles(new HashSet<>(roleCodeList));//把当前用户的所有角色放进授权信息对象中
        //下面的代码精细到操作权限的配置
        /*List<Long> roleIdList = empRoleRelationService.getRoleIds(employeeVO.getId());
        Set<String> actionIdSet = roleAndActionService.getActionsByRoleIdList(roleIdList);
        info.addStringPermissions(actionIdSet);*/
        return info;
    }

}