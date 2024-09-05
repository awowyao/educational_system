package com.cwy.educationaladministrationsystem.mapper.dean;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.exam;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface examMapper extends BaseMapper<Map> {

    List<exam> selectExamList(@Param("acId") Integer acId,
                              @Param("specialtyName") String specialty_name,
                              @Param("selectData")  String selectData,
                              @Param("deanId")  Integer deanId);


    List<course> BySpecialtyGetCourse(@Param("specialty") String specialty);

    Integer addExam(exam data);

    Integer deleteExam(@Param("id") Integer id);

    exam selectByIdExam(@Param("id") Integer id);

    Integer editExam(exam data);

    Integer editStudentScores(@Param("id") Integer studentExamId,
                              @Param("scores") Integer scores);

    void addStudentExam(@Param("StudentId") Integer id, @Param("examId") Integer id1);

    void deleteStudentExam(@Param("id") Integer id);

    exam selectByCourseIdGetExam(@Param("id") Integer id);

    void ComplainteditStudentScores(@Param("id") Integer studentExamId);

    exam selectByElectiveCourseIdGetExam(@Param("id") Integer cid);

    void deleteStudentIdExamId(@Param("studentId")Integer studentId, @Param("examId") Integer id);
}
