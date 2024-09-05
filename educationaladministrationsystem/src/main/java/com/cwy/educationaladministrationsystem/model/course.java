package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class course {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "specialty_name")
    private String specialtyName;
    @TableField(value = "textbooks")
    private String textbooks;
    @TableField(value = "ac_id")
    private Integer academyId;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    private String academyName;
    private List<Integer> TeacherList;

    private String examName;
    private Integer scores;
    private Integer scoresState;
    private String teacherName;
    private Integer teacherId;
    private Integer seId;
    private String EleCourseName;
}
