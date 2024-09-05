package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student_table")
public class student {
    @TableId(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "sex")
    private Integer sex;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "guardian")
    private String guardian;
    @TableField(value = "guardian_phone")
    private String guardianPhone;
    @TableField(value = "curriculum_id")
    private Integer curriculumId;
    @TableField(value = "academy_id")
    private Integer academyId;
    private String cover;
    private String email;
    @TableField(value = "class_id")
    private Integer classId;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    @TableField(exist = false, value = "className")
    @JsonIgnore
    private String password;
    private String className;
    private String academyName;
    private Integer scores;
    private Integer scoresState;
    private String courseName;
    private String seId;
    private Integer complaintState;
}
