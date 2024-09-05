package com.cwy.educationaladministrationsystem.service.imp;

import com.cwy.educationaladministrationsystem.mapper.adminMapper;
import com.cwy.educationaladministrationsystem.mapper.deanMapper;
import com.cwy.educationaladministrationsystem.mapper.loginMapper;
import com.cwy.educationaladministrationsystem.model.dean;
import com.cwy.educationaladministrationsystem.model.student;
import com.cwy.educationaladministrationsystem.model.teacher;
import com.cwy.educationaladministrationsystem.model.user;
import com.cwy.educationaladministrationsystem.service.loginService;
import com.cwy.educationaladministrationsystem.util.EmailUtil;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.cwy.educationaladministrationsystem.util.TokenApi;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;

@Service
public class loginServiceImp implements loginService {
    @Autowired
    private loginMapper loginmapper;

    @Autowired
    private deanMapper deanmapper;
    @Autowired
    private adminMapper adminmapper;
    private String Salt = "jiaowuguan";
    @Override
    public user userLogin(String userName, String password, Integer userType) {
        user userData = new user();
        password = userName + Salt + password;
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (userType == 1){
            userData = loginmapper.studentLogin(userName, password);
        } else if (userType == 2) {
            userData = loginmapper.teacherLogin(userName, password);
        } else if (userType == 3) {
            userData = loginmapper.deanLogin(userName, password);
        } else if (userType == 4) {
            userData = loginmapper.adminLogin(userName, password);
        }

        if (userData != null) {
            userData.setUserType(userType);
            String token = TokenApi.getToken(userData.getName(), userData.getPassword(),userData.getId(), userData.getUserType());
            userData.setToken(token);
        }
        return userData;
    }

    @Override
    public Integer updateStudentPassword(Integer studentId, String oldPassword, String newPassword) {
        oldPassword = studentId + Salt + oldPassword;
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());

        user userData = loginmapper.studentLogin(studentId.toString(), oldPassword);

        if (userData != null){
            newPassword = studentId + Salt + newPassword;
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
            loginmapper.updateStudentPassword(studentId, newPassword);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateTeacherPassword(Integer Id, String oldPassword, String newPassword) {
        oldPassword = Id + Salt + oldPassword;
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        user userData = loginmapper.teacherLogin(Id.toString(), oldPassword);

        if (userData != null){
            newPassword = Id + Salt + newPassword;
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
            loginmapper.resetTeacherPassword(Id, newPassword);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateDeanPassword(Integer id, String oldPassword, String newPassword) {
        oldPassword = id + Salt + oldPassword;
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        user userData = loginmapper.deanLogin(id.toString(), oldPassword);

        if (userData != null){
            newPassword = id + Salt + newPassword;
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
            loginmapper.updateDeanPassword(id, newPassword);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateAdminPassword(String userName, String oldPassword, String newPassword) {
        oldPassword = userName + Salt + oldPassword;
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        user userData = loginmapper.adminLogin(userName, oldPassword);

        if (userData != null){
            newPassword = userName + Salt + newPassword;
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
            loginmapper.updateAdminPassword(userName, newPassword);
            return 1;
        }
        return 0;
    }

    @Override
    public void editUserCover(Integer id, Integer userType, String imagePath) {
        if (userType == 1){
            loginmapper.editStudentUserCover(id, imagePath);
        } else if (userType == 2) {
            loginmapper.editTeacherUserCover(id, imagePath);
        } else if (userType == 3) {
            loginmapper.editDeanUserCover(id, imagePath);
        } else if (userType == 4) {
            loginmapper.editAdminUserCover(id, imagePath);
        }
    }

    @Override
    public String sentForgotPassword(Integer id, Integer type) {
        String sender = "547779262@qq.com";
        boolean state = false;
        String mailContent = "点击修改密码：http://127.0.0.1:8082/forgotPassword?uid=%s";
        if (type == 1) {
            student data =  deanmapper.selectByIdStudent(id);
            String token = TokenApi.getToken(data.getName(),data.getPassword(), data.getId(), type);
            mailContent = String.format(mailContent, token);
            state = EmailUtil.sendTextMail(sender, data.getEmail(), "忘记密码修改", mailContent);
            return data.getEmail();
        } else if(type == 2) {
            teacher data =  deanmapper.selectByIdTeacher(id);
            String token = TokenApi.getToken(data.getName(),data.getPassword(), data.getId(), type);
            mailContent = String.format(mailContent, token);
            state = EmailUtil.sendTextMail(sender, data.getEmail(), "忘记密码修改", mailContent);
            return data.getEmail();
        } else if(type == 3) {
            dean data =  adminmapper.GetDeanData(id);
            System.out.println(data.getEmail());
            String token = TokenApi.getToken(data.getName(),data.getPassword(), data.getId(), type);
            mailContent = String.format(mailContent, token);
            state = EmailUtil.sendTextMail(sender, data.getEmail(), "忘记密码修改", mailContent);
            System.out.println(state);
            return data.getEmail();
        }
        return null;
    }

    @Override
    public Integer ForgotPassword(String uid, String userPwd, String cheackUserPwd) {
        Map<String, String> UserMap = TokenApi.parseToken(uid);
        String userId = UserMap.get("uid");
        Integer type = Integer.valueOf(UserMap.get("userType"));
        String UserPas = UserMap.get("UserPws");

        if (type == 1) {
            user userData = loginmapper.studentLogin(userId,UserPas);
            if (userData !=null){
                userPwd = userId + Salt + userPwd;
                userPwd = DigestUtils.md5DigestAsHex(userPwd.getBytes());
                loginmapper.updateStudentPassword(Integer.valueOf(userId), userPwd);
                return 1;
            }

        } else if(type == 2) {
            user userData = loginmapper.teacherLogin(userId,UserPas);
            if (userData !=null){
                userPwd = userId + Salt + userPwd;
                userPwd = DigestUtils.md5DigestAsHex(userPwd.getBytes());
                loginmapper.resetTeacherPassword(Integer.valueOf(userId), userPwd);
                return 1;
            }
        } else if(type == 3) {
            user userData = loginmapper.deanLogin(userId,UserPas);
            if (userData !=null){
                userPwd = userId + Salt + userPwd;
                userPwd = DigestUtils.md5DigestAsHex(userPwd.getBytes());
                loginmapper.updateDeanPassword(Integer.valueOf(userId), userPwd);
                return 1;
            }

        }
        return null;
    }

    @Override
    public Integer resetStudentPassword(Integer studentId) {
        String Password =DigestUtils.md5DigestAsHex("123456".getBytes());
        Password = studentId + Salt + Password;
        Password = DigestUtils.md5DigestAsHex(Password.getBytes());
        loginmapper.resetStudentPassword(studentId, Password);
        return 1;
    }

    @Override
    public Integer resetTeacherPassword(Integer teacherId) {
        String Password =DigestUtils.md5DigestAsHex("123456".getBytes());
        Password = teacherId + Salt + Password;
        Password = DigestUtils.md5DigestAsHex(Password.getBytes());
        loginmapper.resetTeacherPassword(teacherId, Password);
        return null;
    }


}
