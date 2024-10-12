package com.cwy.educationaladministrationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cwy.educationaladministrationsystem.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface deanMapper extends BaseMapper<academy> {
    void addTeacher(teacher data);

    void addStudent(student data);

    void addClass(ClassT data);

    void StudentSetClass(@Param("id") Integer id,
                         @Param("classId") Integer classId,
                         @Param("date") Date date);

    void StudentSetAcademy(@Param("id") Integer id,
                           @Param("academyId") Integer academyId,
                           @Param("date") Date date);

    void editTeacher(teacher data);

    void editStudent(student data);

    List<student> selectStudent(@Param("acId") Integer acId,
                                @Param("classId") Integer classId,
                                @Param("SelectStudent") String SelectStudent,
                                @Param("deanId") Integer deanId);

    List<teacher> selectTeacher(@Param("acId") Integer acId,
                                @Param("selectData") String selectData,
                                @Param("deanId")  Integer deanId);

    void deleteTeacher(@Param("id") Integer id);

    void deleteStudent(Integer id);

    List<academy> selectAcademy(@Param("academy") String academy,@Param("deanId")  Integer deanId);

    List<ClassT> selectByAcademyIdFindClass(@Param("id") Integer id);

    student selectByIdStudent(@Param("id") Integer studentId);

    teacher selectByIdTeacher(@Param("id")  Integer id);

    List<course> selectCourse(@Param("acId") Integer acId, @Param("selectData") String selectData,
                              @Param("deanId") Integer deanId);

    void addCourse(course data);

    void deleteCourse(@Param("id") Integer id);

    void addTeacherByCourse(@Param("id") Integer id,
                            @Param("teacherId") Integer academyId);

    course ByAcademyIdAndNameSelectCourse(@Param("courseName") String courseName,
                                          @Param("academyId") Integer academyId);

    Integer SelectCourseNub(@Param("courseName") String courseName,
                            @Param("academyId") Integer academyId);

    course selectCourseData(@Param("id") Integer id);

    List<teacher> selectCourseTeacher(@Param("id") Integer id);

    Integer editCourse(course data);

    void delectCourseTeacherById(@Param("id") Integer id);
}
