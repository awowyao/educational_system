package com.cwy.educationaladministrationsystem.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface teacherMapper {
    Integer ByCourseIdClassIdGetTeacher(@Param("classId") Integer classId, @Param("courseId") Integer courseId);
}
