package com.yh.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //前后端分离这里写路径，前后端不分离就写页面
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");
        //前后端分离则没有这个方法，前后端不分离设置登录成功页面
        shiroFilterFactoryBean.setSuccessUrl("");
        //登录后没有权限跳转路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_auth");

        //此处必须new LinkedHashMap否则会出错，因为这个拦截有序的，要按照你配置的顺序拦截，所以不能用无序的map
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        //所有的过滤器在DefaultFilter看

        //退出的路径
        filterChainDefinitionMap.put("/logout","logout");
        //游客可以访问的路径
        filterChainDefinitionMap.put("/pub/**","anon");
        //登陆后可以访问的路径
        filterChainDefinitionMap.put("/authc/**","authc");
        //登陆后有admin角色才可以访问的路径
        filterChainDefinitionMap.put("/root/**","roles[root]");
        //有添加权限的才可以访问的路径
        filterChainDefinitionMap.put("/video/add","perms[video_add]");
        //防止有未拦截的路径，如果有就走这个过滤器
        filterChainDefinitionMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setSessionManager(sessionManager());

        securityManager.setRealm(customerRealm());
        return securityManager;
    }
    @Bean
    public CustomerRealm customerRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
//        customerRealm.setCredentialsMatcher(matcher());
        return customerRealm;
    }

    @Bean
    public HashedCredentialsMatcher matcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        //使用MD5加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置加密次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public SessionManager sessionManager(){

        CustomerSessionManager customerSessionManager = new CustomerSessionManager();
//        customerSessionManager.setGlobalSessionTimeout(20000);
        return customerSessionManager;
    }


}
