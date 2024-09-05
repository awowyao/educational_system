package com.cwy.educationaladministrationsystem.mapper;

import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.electiveCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface courseMapper {


    Integer addElectiveCourse(electiveCourse data);

    List<electiveCourse> GetElectiveCourse(@Param("acId") Integer acId,
                                           @Param("teacherId") Integer teacherId,
                                           @Param("select") String select,
                                           @Param("deanId") Integer deanId);

    electiveCourse ByIdGetElectiveCourse(@Param("id") Integer id);

    Integer editElectiveCourse(electiveCourse date);

    Integer deleteElectiveCourse(@Param("id") Integer id);

    Integer chooseCourse(@Param("studentId") Integer studentId, @Param("cid") Integer cid);


    List<electiveCourse> ByStudentIdGetElectiveCourse(@Param("studentId") Integer studentId);

    List<electiveCourse> StudentGetElectiveCourse(@Param("acId") Integer academyId,
                                                  @Param("teacherId") Integer teacherId,
                                                  @Param("select")String select,
                                                  @Param("studentId")Integer studentId);

    void CancelChooseCourse(@Param("cid") Integer cid,@Param("studentId") Integer studentId);

    List<course> selectByClassIdTeacherIdGetCourse(@Param("id") Integer id,@Param("teacherId")  Integer teacherId);

    electiveCourse selectByIdElectiveCourse(@Param("id") Integer cid);
}
