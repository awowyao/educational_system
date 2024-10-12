package com.cwy.educationaladministrationsystem.mapper;

import com.cwy.educationaladministrationsystem.model.user;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface loginMapper {
    user studentLogin(@Param("userName") String userName, @Param("password") String password);

    user teacherLogin(@Param("userName") String userName, @Param("password") String password);

    user deanLogin(@Param("userName") String userName, @Param("password") String password);

    void updateStudentPassword(@Param("studentId") Integer studentId, @Param("newPassword") String newPassword);

    void resetStudentPassword(@Param("studentId") Integer studentId, @Param("password") String password);

    void resetTeacherPassword(@Param("teacherId") Integer teacherId, @Param("password") String password);

    user adminLogin(@Param("userName") String userName,@Param("password") String password);

    void updateDeanPassword(@Param("id") Integer id,@Param("password") String newPassword);

    void updateAdminPassword(@Param("userName") String userName,@Param("password") String newPassword);

    void editStudentUserCover(Integer id, String imagePath);

    void editTeacherUserCover(Integer id, String imagePath);

    void editDeanUserCover(Integer id, String imagePath);

    void editAdminUserCover(Integer id, String imagePath);
}
