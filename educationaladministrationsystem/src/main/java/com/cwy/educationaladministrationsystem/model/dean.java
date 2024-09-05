package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class dean {
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "email")
    private String email;
    private Integer sex;
    private String cover;

    @JsonIgnore
    private String password;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    private List<Object> AuthorityList;
}
