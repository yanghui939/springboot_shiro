package com.yh.dao;

import com.yh.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao {

    public List<Role> findRoleListByUserId(int userId);

}
