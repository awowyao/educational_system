package com.cwy.educationaladministrationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cwy.educationaladministrationsystem.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface classMapper extends BaseMapper<Map> {

    List<ClassT> selectClass(@Param("specialtyName") String specialtyName,
                             @Param("academyId") Integer academyId,
                             @Param("select") String select,
                             @Param("deanId") Integer deanId);

    List<specialty> selectByAcademyIdFindspecialt(@Param("id") Integer id);

    void deleteClass(@Param("id") Integer id);

    List<ClassT> selectByIdClass(@Param("id") Integer id);

    void EditClass(ClassT data);

    List<curriculum> GetCurriculum(@Param("id") Integer id);

    Integer addCurriculum(curriculum cdata);

    List<course> ByAcademyIdGetCourse(@Param("id") Integer academyId);

    List<teacher> ByCourseIdGetTeacher(@Param("id") Integer id);

    Integer editCurriculum(curriculum serviceData);


    void addClassCourse(@Param("classId") Integer classId,
                        @Param("courseId") Integer courseId,
                        @Param("teacherId") Integer teacherId);

    List<course> GetClassCourse(@Param("classId") Integer classId);

    void DeleteClassCourse(@Param("classId") Integer classId, @Param("courseId") Integer courseId);

    List<ClassT> GetByTeacherClass(@Param("teacherId") Integer teacherId);

    List<ClassT> selectByTeacherGetClass(@Param("id") Integer id);
}

