package com.cwy.educationaladministrationsystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class exam {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "exam_begin_time")
    private Date examBeginTime;
    @TableField(value = "exam_time")
    private Integer examTime;

    @TableField(value = "academy_id")
    private Integer academyId;
    @TableField(value = "specialty_name")
    private String specialtyName;
    private Integer electveCourseId;
    @TableField(value = "course_id")
    private Integer courseId;

    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    private String courseName;
    private String electveCourseName;
}
