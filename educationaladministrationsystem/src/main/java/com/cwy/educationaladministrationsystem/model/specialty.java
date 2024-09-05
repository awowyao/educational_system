package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class specialty {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "academy_id")
    private Integer academy_id;
    @TableField(value = "create_time")
    private Date create_time;
    @TableField(value = "update_time")
    private Date update_time;
    private Integer classNumber;
    private Integer studentNumber;
}
