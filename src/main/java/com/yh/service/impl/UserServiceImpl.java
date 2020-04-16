package com.yh.service.impl;

import com.yh.dao.AuthorityDao;
import com.yh.dao.RoleDao;
import com.yh.dao.UserDao;
import com.yh.pojo.Authority;
import com.yh.pojo.Role;
import com.yh.pojo.User;
import com.yh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AuthorityDao authorityDao;
    @Override
    public User findUserByUserName(String userName) {
        User userByUserName = userDao.findUserByUserName(userName);
        List<Role> roleList = roleDao.findRoleListByUserId(userByUserName.getUserId());
        for(Role role :roleList){
            List<Authority> authorityList = authorityDao.findAuthListByUserId(role.getRoleId());
            role.setAuthoritys(authorityList);
        }
        userByUserName.setRoles(roleList);
        return userByUserName;
    }
}
