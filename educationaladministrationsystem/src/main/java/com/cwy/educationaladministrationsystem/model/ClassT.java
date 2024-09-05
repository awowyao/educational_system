package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ClassT {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "number")
    private Integer number;
    @TableField(value = "curriculum_id")
    private Integer curriculumId;
    @TableField(value = "academy_id")
    private Integer academyId;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    private String year;
    @TableField(value = "specialty_name")
    private String specialtyName;

}
