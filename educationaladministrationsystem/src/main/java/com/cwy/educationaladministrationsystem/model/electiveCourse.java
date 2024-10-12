package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class electiveCourse {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "teacher_id")
    private Integer teacherId;
    @TableField(value = "academy_id")
    private Integer academyId;
    @TableField(value = "total")
    private Integer total;
    @TableField(value = "class_time")
    private String classTime;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    private String teacherName;
    private Integer studentId;
    private String academyName;
    private Integer chooseNub;
}
