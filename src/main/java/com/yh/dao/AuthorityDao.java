package com.yh.dao;

import com.yh.pojo.Authority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityDao {

    public List<Authority> findAuthListByUserId(int roleId);
}
