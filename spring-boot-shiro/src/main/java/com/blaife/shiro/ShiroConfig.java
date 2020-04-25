package com.blaife.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Blaife
 * @description TODO
 * @data 2020/4/24 23:23
 */
@Configuration
public class ShiroConfig {

    /**
     * 功能描述: 创建realm
     */
    @Bean
    UserRealm getRealm() {
        return new UserRealm();
    }

    /**
     * 功能描述: 创建DefaultWebSecurityManager
     */
    @Bean()
    DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(getRealm());
        return securityManager;
    }

    /**
     * 功能描述: 创建ShiroFilterFactoryBean
     *
     * Shiro Starter不再需要 ShiroFilterFactoryBean 实例了，替代它的是 ShiroFilterChainDefinition ，在这里定义 Shiro 的路径匹配规则即可。
     */
    @Bean
    ShiroFilterChainDefinition getShiroFilterFactoryBean() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();

        /* 添加shiro内置过滤器 -- 实现权限相关的拦截器
         * 常用过滤器：
         *      anon: 无需认证（登录)可以访问
         *      authc：必须认证才可以访问
         *      user: 如果使用remberMe的功能可以登录
         *      perms: 该资源必须得到资源授权才可以访问
         *      role: 该资源必须得到角色授权才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/user/getUser", "anon");
        filterMap.put("/user/toLogin", "anon");
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/**", "authc");

        definition.addPathDefinitions(filterMap);

        /* Shiro Starter中ShiroFilterChainDefinition方法中部分的配置内容交给了配置文件 */

        return definition;
    }

    /**
     * 功能描述： 配置ShiroDialect, 用于thymeleaf和shiro标签配合使用
     */
    @Bean
    ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}

