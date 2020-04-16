package com.yh.config;

import com.yh.pojo.Authority;
import com.yh.pojo.Role;
import com.yh.pojo.User;
import com.yh.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义realm
 */
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        String userName = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUserName(userName);
        System.out.println(user);
        //得到角色对象集合
        List<Role> roles = user.getRoles();

        //定义两个集合，储存角色和权限
        List<String>  stringRoleList = new ArrayList<>();
        List<String>  stringAuthorityList = new ArrayList<>();

        for(Role role:roles){
            stringRoleList.add(role.getRoleName());
            for(Authority authority:role.getAuthoritys()){
                stringAuthorityList.add(authority.getAuthName());
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleList);
        simpleAuthorizationInfo.addStringPermissions(stringAuthorityList);

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        //获取用户输入的用户信息
        String userName = (String) authenticationToken.getPrincipal();

        User user = userService.findUserByUserName(userName);

        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return null;
        }
        return new SimpleAuthenticationInfo(userName, user.getPassword(), this.getClass().getName());
    }
}
