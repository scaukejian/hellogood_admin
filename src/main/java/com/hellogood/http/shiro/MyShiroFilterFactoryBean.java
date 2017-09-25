package com.hellogood.http.shiro;

import com.hellogood.domain.RoleActionRelationKey;
import com.hellogood.http.vo.ActionVO;
import com.hellogood.http.vo.RoleVO;
import com.hellogood.service.ActionService;
import com.hellogood.service.RoleAndActionService;
import com.hellogood.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro过滤工厂，进行自定义操作，包括动态加载数据库配置权限，过滤 jsessionid等url后缀等
 * Created by KJ on 2017/9/19.
 */
public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    @Autowired
    private RoleAndActionService roleAndActionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ActionService actionService;
    public static final String ROLE_STRING = "roles[{0}]";//有且仅有一个角色
    public static final String ROLEOR_STRING = "roleOrFilter[{0}]";//有多个角色，只要满足一个条件即可
    /** 记录配置中的过滤链 */
    public static String filterChainDefinitions = "";//这个要和配置文件中的名称要一样

    /**
     * 初始化设置过滤链（在配置文件的基础上，加上数据库的动态url权限配置）
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {
        filterChainDefinitions = definitions;// 记录配置的静态过滤链
        Map<String, String> otherChains = new HashMap<>();
        Map<String, String> hasRoleCodeMap = new HashMap<>();
        //拿到数据库所有的角色-资源配置
        List<RoleActionRelationKey> roleActionRelationKeyList = roleAndActionService.getAllRoleAndActions();
        for (RoleActionRelationKey roleActionRelationKey : roleActionRelationKeyList) {
            ActionVO actionVO = actionService.get(roleActionRelationKey.getActionId());
            RoleVO roleVO =  roleService.get(roleActionRelationKey.getRoleId());
            String url = actionVO.getUrl();
            String roleCode = roleVO.getCode();
            if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(roleCode)) {
                if (!url.startsWith("/")) url = "/" + url; //url必须是webapp下的相对目录，所有url强制用“/”开头
                if (url.contains("?")) url = url.substring(0, url.indexOf("?"));//过滤掉url后面的参数，保证授权操作对应的是页面资源文件
                String oldStr = hasRoleCodeMap.get(url);//拿到当前url的角色集合
                //判断当前url是否已经存在角色授权，如果有，把原来的角色连接现在的角色进行或授权
                //或授权是指，只要满足两个角色之中的一个，即可授权访问
                if (StringUtils.isNotBlank(oldStr)) {
                    oldStr += ("," + roleCode);
                } else {
                    oldStr = roleCode;
                }
                hasRoleCodeMap.put(url, oldStr); //记录当前url的角色集合
                if (oldStr.contains(",")) { //当前url被赋权给多个角色
                    otherChains.put(url, MessageFormat.format(ROLEOR_STRING, oldStr)); //roleOrFilter[{0}]
                } else {//当前url有且只有一个角色授权
                    otherChains.put(url, MessageFormat.format(ROLE_STRING, oldStr));//roles[{0}]
                }
            }
        }

        Ini ini = new Ini();
        ini.load(filterChainDefinitions); // 加载配置默认的过滤链
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        section.putAll(otherChains); // 加上数据库中过滤链
        setFilterChainDefinitionMap(section);
    }

    @Override
    public Class getObjectType() {
        return MySpringShiroFilter.class;
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {

        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }
        FilterChainManager manager = createFilterChainManager();

        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        return new MySpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    private static final class MySpringShiroFilter extends AbstractShiroFilter {

        protected MySpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }

        /**
         * 过滤URL中的 JESSIONID
         * @param orig
         * @param request
         * @return
         */
        @Override
        protected ServletResponse wrapServletResponse(HttpServletResponse orig, ShiroHttpServletRequest request) {
            return new URLRemoveJESSIONID(orig, getServletContext(), request);
        }
    }
}