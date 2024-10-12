package com.cwy.educationaladministrationsystem.model;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class academy {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;

    @TableField(value = "create_time")
    private Date create_time;
    @TableField(value = "update_time")
    private Date update_time;
    private Integer teacherNumber;
    private Integer studentNumber;
}
