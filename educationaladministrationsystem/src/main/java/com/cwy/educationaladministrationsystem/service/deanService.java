package com.cwy.educationaladministrationsystem.service;

import com.cwy.educationaladministrationsystem.model.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface deanService {
    Integer addTeacher(teacher data);

    Integer addStudent(student data);

    Integer addClass(ClassT data);

    Integer StudentSetClass(Integer id, Integer classId);

    Integer StudentSetAcademy(Integer id, Integer academyId);

    Integer editTeacher(teacher data);

    Integer editStudent(student data);

    PageInfo<student> selectStudent(Integer page, Integer pageSize, Integer acId, Integer classId, String SelectStudent, Integer deanId);

    PageInfo<teacher> selectTeacher(int page, int pageSize, Integer acId, String selectStudent,Integer deanId);

    Integer deleteTeacher(Integer id);

    Integer deleteStudent(Integer id);

    List<Map> selectAcademyOrClass(String academy, Integer deanId);

    List<ClassT> selectClass(Integer academyId);

    student selectByIdStudent(Integer studentId);

    teacher selectByIdTeacher(Integer id);

    PageInfo<course> selectCourse(Integer page, Integer pageSize, Integer acId, String selectData, Integer deanId);

    Integer addCourse(course data);

    Integer deleteCourse(Integer id);

    Integer addTeacherByCourse(Integer id, List<Integer> academyList);

    course ByAcademyIdAndNameSelectCourse(String courseName, Integer academyId);

    course selectCourseData(Integer id);

    Integer editCourse(course data);


    Integer editDeanUserData(dean data);
}
