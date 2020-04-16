package com.yh.controller;

import com.yh.dao.AuthorityDao;
import com.yh.dao.RoleDao;
import com.yh.dao.UserDao;
import com.yh.pojo.Authority;
import com.yh.pojo.Role;
import com.yh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AuthorityDao authorityDao;

    @RequestMapping("test")
    public User say(){
        User yanghui = userDao.findUserByUserName("yanghui");
        List<Role> roleList = roleDao.findRoleListByUserId(yanghui.getUserId());
        for(Role role :roleList){
            List<Authority> authorityList = authorityDao.findAuthListByUserId(role.getRoleId());
            role.setAuthoritys(authorityList);
        }
        yanghui.setRoles(roleList);
        return yanghui;
    }
}
