package com.cwy.educationaladministrationsystem.service.dean;

import com.cwy.educationaladministrationsystem.model.ClassT;
import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.exam;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface examService {


    PageInfo<exam> selectExamList(Integer page, Integer pageSize, Integer acId, String specialty_name, String selectData, Integer deanId);


    List<course> BySpecialtyGetCourse(String specialty);


    Integer addExam(exam data);

    Integer deleteExam(Integer id);

    exam selectByIdExam(Integer id);

    Integer editExam(exam data);
}
