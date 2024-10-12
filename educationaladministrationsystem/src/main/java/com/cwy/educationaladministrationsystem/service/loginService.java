package com.cwy.educationaladministrationsystem.service;

import com.cwy.educationaladministrationsystem.model.user;

public interface loginService {
    user userLogin(String userName, String password, Integer userType);

    Integer updateStudentPassword(Integer studentId, String oldPassword, String newPassword);

    Integer resetStudentPassword(Integer studentId);

    Integer resetTeacherPassword(Integer teacherId);

    Integer updateTeacherPassword(Integer Id, String oldPassword, String newPassword);

    Integer updateDeanPassword(Integer id, String oldPassword, String newPassword);

    Integer updateAdminPassword(String userName, String oldPassword, String newPassword);

    void editUserCover(Integer id, Integer userType, String imagePath);

    String sentForgotPassword(Integer id, Integer type);

    Integer ForgotPassword(String uid, String userPwd, String cheackUserPwd);
}
