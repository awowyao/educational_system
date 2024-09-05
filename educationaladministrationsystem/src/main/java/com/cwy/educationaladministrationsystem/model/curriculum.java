package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class curriculum {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "curriculum_data")
    private String curriculumData;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
}
