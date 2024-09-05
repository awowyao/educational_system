package com.cwy.educationaladministrationsystem.service;

import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.student;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface studentService {

    PageInfo<student> GetByTeacherIdStudent(Integer page, Integer pageSize, Integer teacherId, Integer classId, String select);

    PageInfo<student> GetByExamStudent(Integer page, Integer pageSize, Integer teacherId, Integer classId, String select,Integer courseId);

    Integer editStudentScores(Integer studentExamId, Integer scores, Integer def);

    PageInfo<student> GetStudentComplaint(Integer page, Integer pageSize, Integer teacherId, Integer classId, String select, Integer courseId);


    Integer DeleteStudentComplaint(Integer studentExamId);

    PageInfo<course> GetStudentScores(Integer page, Integer pageSize, Integer studentId, String select, String year);


    Integer ComplaintCourse(Integer courseId, Integer teacherId, Integer seId);


    PageInfo<Map> GetComplaintTable(Integer studentId, Integer page, Integer pageSize, String select);


}
