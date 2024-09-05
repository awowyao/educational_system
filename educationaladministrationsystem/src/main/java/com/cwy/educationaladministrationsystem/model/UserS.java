package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("user_table")
public class UserS implements Serializable {

    private static final long serialVersionUID = 1;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
}
