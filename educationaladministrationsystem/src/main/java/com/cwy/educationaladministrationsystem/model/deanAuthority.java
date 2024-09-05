package com.cwy.educationaladministrationsystem.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class deanAuthority {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "dean_id")
    private Integer deanId;
    private Integer academyId;

    private String authority;
    private String operateLog;

}
