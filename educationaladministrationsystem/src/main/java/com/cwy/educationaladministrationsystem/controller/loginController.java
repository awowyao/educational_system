package com.cwy.educationaladministrationsystem.controller;

import com.cwy.educationaladministrationsystem.intercept.StudentLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.teacherLoginIntercept;
import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.result;
import com.cwy.educationaladministrationsystem.model.user;
import com.cwy.educationaladministrationsystem.service.loginService;
import com.cwy.educationaladministrationsystem.util.EmailUtil;
import com.cwy.educationaladministrationsystem.util.ResultUtil;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.cwy.educationaladministrationsystem.util.uploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/loginApi")
public class loginController {
    @Autowired
    private loginService loginservice;
    @PostMapping("/login")
    public ResponseEntity<result> login(@RequestBody Map<String, String> data){
        String userName = data.get("userName");
        String password = data.get("password");
        Integer userType = Integer.valueOf(data.get("userType"));
        user loginState = loginservice.userLogin(userName, password, userType);
        if (loginState != null){
            return ResultUtil.success(200, "登录成功", loginState);
        }
        return ResultUtil.error(401, "密码错误");
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<result> adminLogin(@RequestBody Map<String, String> adminLogin){
        String userName = adminLogin.get("userName");
        String password = adminLogin.get("password");
        user loginState = loginservice.userLogin(userName, password, 4);
        if (loginState != null){
            return ResultUtil.success(200, "登录成功", loginState);
        }
        return ResultUtil.error(401, "密码错误");
    }
    @deanLoginIntercept
    @StudentLoginIntercept
    @teacherLoginIntercept
    @PostMapping("/updatePassword")
    public ResponseEntity<result> updatePassword(@RequestBody Map<String, String> data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer Id = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        Integer userType = Integer.valueOf(ThreadLocalMap.get("userType").toString());
        String oldPassword = data.get("oldPass");
        String newPassword = data.get("pass");
        String checkPass = data.get("checkPass");
        Integer state = 0;
        if (newPassword.equals(checkPass)){
            if (userType == 1) {
                state = loginservice.updateStudentPassword(Id, oldPassword, newPassword);
            } else if (userType == 2) {
                state = loginservice.updateTeacherPassword(Id, oldPassword, newPassword);
            } else if (userType == 3) {
                state = loginservice.updateDeanPassword(Id, oldPassword, newPassword);
            } else if (userType == 4) {
                String userName = ThreadLocalMap.get("userName").toString().toString();
                state = loginservice.updateAdminPassword(userName, oldPassword, newPassword);
            }

        }
        if (state == 1){
            return ResultUtil.success(200,"修改成功", state);
        }else {
            return ResultUtil.error(403,"修改失败");
        }
    }
    @deanLoginIntercept
    @StudentLoginIntercept
    @teacherLoginIntercept
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseEntity<result> uploadImage(@RequestParam(value = "file") MultipartFile multipartFile) {
        // 调用写好的工具类
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer id = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        Integer userType = Integer.valueOf(ThreadLocalMap.get("userType").toString());
        String imagePath = uploadImage.upload(multipartFile);

        if(imagePath != null) {
            imagePath = "/static/cover/"+imagePath;
            loginservice.editUserCover(id, userType, imagePath);
            return ResultUtil.success(200,"修改成功",imagePath);
        } else {
            return ResultUtil.error(403,"修改失败");
        }
    }


    @PostMapping("/sentForgotPassword")
    public ResponseEntity<result> sendEmail(@RequestBody Map<String, Integer> data){

        Integer id = data.get("id");
        Integer Type = data.get("Type");
        String emie = loginservice.sentForgotPassword(id, Type);
//        boolean aBoolean = EmailUtil.sendTextMail(sender, receiver, subject, mailContent);
        if (emie!=null){
            //自己写的Result对象
            return ResultUtil.success(200,"发送成功","已向"+emie+"发送邮箱");
        }
        return ResultUtil.error(403,"发送失败！");
    }

    @PostMapping("/ForgotPassword")
    public ResponseEntity<result> ForgotPassword(@RequestBody Map<String, String> data){

        String uid = data.get("uid");
        String UserPwd = data.get("UserPwd");
        String cheackUserPwd = data.get("checkUserPwd");
        Integer state = loginservice.ForgotPassword(uid, UserPwd, cheackUserPwd);
//        boolean aBoolean = EmailUtil.sendTextMail(sender, receiver, subject, mailContent);
        if (state != null){
            //自己写的Result对象
            return ResultUtil.success(200,"修改成功","");
        }
        return ResultUtil.error(403,"修改失败！");
    }
}
