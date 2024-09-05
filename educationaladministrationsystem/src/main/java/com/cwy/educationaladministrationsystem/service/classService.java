package com.cwy.educationaladministrationsystem.service;

import com.cwy.educationaladministrationsystem.model.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface classService {

    PageInfo<ClassT> selectClass(Integer page, Integer pageSize, String specialtyName, Integer academyId, String select, Integer deanId);

    List<Map> selectAcademyOrSpecialty(String academy, Integer deanId);

    List<specialty> selectSpecialty(Integer academyId);

    Integer deleteClass(Integer id);

    List<ClassT> selectByIdClass(Integer id);

    Integer EditClass(ClassT data);

    List<curriculum> GetCurriculum(Integer id, Integer cid, Integer lv);


    List<course> ByAcademyIdGetCourse(Integer academyId);

    List<teacher> ByCourseIdGetTeacher(Integer id);

    Integer editCurriculum(Integer id, List<Map> curriculumData, Map oldCurData, Map newCurData, Integer cid);

    Integer editTeacherCurriculum(Integer id, List<Map> curriculumData, Map oldCurData, Map newCurData, Integer cid);


    Integer addClassCourse(Integer classId, Integer courseId, Integer teacherId);

    List<course> GetClassCourse(Integer classId);

    Integer DeleteClassCourse(Integer classId, Integer courseId);


    List<ClassT> GetByTeacherClass(Integer teacherId);

    List<ClassT> selectByTeacherGetClass(Integer id);

    List<Map<String, Object>> GetClassAndCourse(Integer teacherId);
}
