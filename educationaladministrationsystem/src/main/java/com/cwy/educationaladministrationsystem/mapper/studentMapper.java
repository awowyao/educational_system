package com.cwy.educationaladministrationsystem.mapper;

import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface studentMapper {
    List<student> GetByTeacherIdStudent(@Param("teacherId") Integer teacherId,
                                        @Param("classId") Integer classId,
                                        @Param("select") String select);

    List<student> GetByExamStudent(@Param("teacherId") Integer teacherId,
                                   @Param("classId") Integer classId,
                                   @Param("select") String select,
                                   @Param("courseId") Integer courseId);
    List<student> GetByEleExamStudent(@Param("teacherId") Integer teacherId,
                                      @Param("classId") Integer classId,
                                      @Param("select") String select,
                                      @Param("courseId") Integer courseId);
    List<student> GetStudentComplaint(@Param("teacherId") Integer teacherId,
                                      @Param("classId") Integer classId,
                                      @Param("select") String select);
    List<student> GetStudentEleComplaint(@Param("teacherId") Integer teacherId,
                                         @Param("classId") Integer classId,
                                         @Param("select") String select,
                                         @Param("courseId") Integer courseId);
    Integer DeleteStudentComplaint(@Param("id") Integer studentExamId);

    List<course> GetStudentScores(@Param("id") Integer studentId,
                                  @Param("select") String select,
                                  @Param("year") String year);

    Integer ComplaintCourse(@Param("courseId") Integer courseId,
                            @Param("teacherId") Integer teacherId,
                            @Param("seId") Integer seId,
                            @Param("date") Date date);

    List<Map> GetComplaintTable(@Param("id") Integer studentId, @Param("select") String select);
    List<Map> GetEleComplaintTable(@Param("id") Integer studentId, @Param("select") String select);
    List<student> selectBycourseStudent(@Param("id") Integer courseId);

    void addStudentCourse(@Param("id") Integer id,
                          @Param("courseId") Integer courseId,
                          @Param("teacherId") Integer teacherId);

    void deleteStudentCourse(@Param("id") Integer id, @Param("courseId") Integer courseId);

    List<student> ByElectiveIdGetStudent(@Param("id") Integer id);
}
