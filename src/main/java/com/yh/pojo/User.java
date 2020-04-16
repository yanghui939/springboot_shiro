package com.yh.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {

    private String id;

    private int userId;

    private String userName;

    private String password;

    private Date createTime;

    private String salt;

    private List<Role> roles;
}
