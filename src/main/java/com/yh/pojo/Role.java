package com.yh.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Role {

    private String id;
    private int roleId;
    private String roleName;
    private String description;

    private List<Authority> authoritys;
}
