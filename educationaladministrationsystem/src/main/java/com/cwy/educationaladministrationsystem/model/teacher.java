package com.cwy.educationaladministrationsystem.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

@Data
public class teacher {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "sex")
    private Integer sex;
    @TableField(value = "password")
    @JsonIgnore
    private String password;
    @TableField(value = "phone")
    private String phone;
    private String email;
    private String cover;
    @TableField(value = "curriculum_id")
    private Integer curriculumId;
    @TableField(value = "academy_id")
    private Integer academyId;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    private String academyName;

}
