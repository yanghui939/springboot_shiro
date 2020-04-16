package com.yh.dao;

import com.yh.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {


    public User findUserByUserName(String userName);

}
