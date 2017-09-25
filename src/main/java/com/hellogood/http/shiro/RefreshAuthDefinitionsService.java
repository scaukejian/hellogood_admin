package com.hellogood.http.shiro;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

/**
 * 重新加载权限过滤链
 * 当权限发生变化时，调用 reloadFilterChains方法即可刷新权限数据，无需重启服务器，用户重新登录即可生效
 * Created by KJ on 2017/9/21.
 */
@Service
public class RefreshAuthDefinitionsService {

    @Resource
    private MyShiroFilterFactoryBean myShiroFilterFactoryBean;

    public void reloadFilterChains() {
        synchronized (myShiroFilterFactoryBean) {   //强制同步，控制线程安全
            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) myShiroFilterFactoryBean.getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                myShiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                // 重新设置权限
                myShiroFilterFactoryBean.setFilterChainDefinitions(MyShiroFilterFactoryBean.filterChainDefinitions);//传入配置中的filterchains

                Map<String, String> chains = myShiroFilterFactoryBean.getFilterChainDefinitionMap();
                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    for (Map.Entry<String, String> chain : chains.entrySet()) {
                        manager.createChain(chain.getKey(), chain.getValue().replace(" ", ""));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}