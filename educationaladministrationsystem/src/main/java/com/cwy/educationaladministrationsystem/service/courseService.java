package com.cwy.educationaladministrationsystem.service;

import com.cwy.educationaladministrationsystem.model.*;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface courseService {
    Integer addElectiveCourse(electiveCourse data);


    PageInfo<electiveCourse> GetElectiveCourse(Integer page, Integer pageSize, Integer acId, Integer teacherId, String select,Integer deanId);

    electiveCourse ByIdGetElectiveCourse(Integer id);

    Integer editElectiveCourse(electiveCourse data);

    Integer deleteElectiveCourse(Integer id);

    Integer chooseCourse(Integer studentId, Integer cid);

    PageInfo<electiveCourse> StudentGetElectiveCourse(Integer page, Integer pageSize, String select, Integer academyId, Integer teacherId, Integer studentId);


    PageInfo<electiveCourse> ByStudentIdGetElectiveCourse(Integer page, Integer pageSize, Integer studentId, Integer acId, Integer teacherId, String select);

    Integer CancelClass(Integer cid, Integer studentId);


    List<course> selectByClassIdTeacherIdGetCourse(Integer id, Integer teacherId);
}
