package com.cwy.educationaladministrationsystem.controller;

import com.cwy.educationaladministrationsystem.intercept.StudentLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.*;

import com.cwy.educationaladministrationsystem.util.ResultUtil;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.cwy.educationaladministrationsystem.util.adminAuthority;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@Controller
@CrossOrigin
@RequestMapping("/deanApi")
public class deanController {
    @Autowired
    private deanService deanservice;
    @Autowired
    private loginService loginservice;
    @Autowired
    private adminService adminservice;
    @Autowired
    private classService classservice;
    @Autowired
    private courseService courseservice;

    @deanLoginIntercept
    @PostMapping ("/addCourse")
    public ResponseEntity<result> addCourse(@RequestBody course data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "add"))
                return ResultUtil.error(401, "无权限");
        }
        Integer start = deanservice.addCourse(data);
        if (start>0) {
            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该课程");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }
    @deanLoginIntercept
    @PostMapping ("/addTecherByCourse")
    public ResponseEntity<result> addTecherByCourse(@RequestBody Map<String, Object> data){
        Integer id = 0;
        if (data.get("courseId") != null){
            id = Integer.valueOf(data.get("courseId").toString());
        }else {
            String courseName = data.get("courseName").toString();
            Integer academyId = Integer.valueOf(data.get("academyId").toString());
            course courseData = deanservice.ByAcademyIdAndNameSelectCourse(courseName, academyId);
            id = courseData.getId();
        }
        List<Integer> academyList = (List<Integer>) data.get("TeacherList");
        Integer start = deanservice.addTeacherByCourse(id, academyList);

        if (start>0) {

            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该关联");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }
    @deanLoginIntercept
    @PostMapping ("/addTeacher")
    public ResponseEntity<result> addTeacher(@RequestBody teacher data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        String deanName = ThreadLocalMap.get("userName").toString();
        if (ThreadLocalMap.get("userType").toString().equals("3")){

            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "add"))
                return ResultUtil.error(401, "无权限");
            else {
                String log = "id: "+ deanId +", 名字: " + deanName + "添加了名字为" + data.getName() + "的老师";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }
        Integer start = deanservice.addTeacher(data);
        if (start>0) {

            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该教师");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }
    @deanLoginIntercept
    @PostMapping("/addStudent")
    public ResponseEntity<result> addStudent(@RequestBody student data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());

        String deanName = ThreadLocalMap.get("userName").toString();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "add"))
                return ResultUtil.error(401, "无权限");
            else {
                String log = "id: "+ deanId +", 名字: " + deanName + "添加了名字为" + data.getName() + "的学生";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }

        Integer start =  deanservice.addStudent(data);
        if (start>0) {

            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该学生");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }
    // 查询学生
    @deanLoginIntercept
    @GetMapping("/selectStudent")
    public ResponseEntity<result> selectStudent(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                @RequestParam(required = false,defaultValue = "0") String SelectStudent,
                                                @RequestParam(required = false,defaultValue = "0") Integer acId,
                                                @RequestParam(required = false,defaultValue = "0") Integer classId){

        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            deanId = 0;
        }
        PageInfo<student> studentData = deanservice.selectStudent(page, pageSize, acId, classId, SelectStudent, deanId);
        return ResultUtil.success(201,"获取成功", studentData);
    }

    @deanLoginIntercept
    @GetMapping("/selectByTeacherGetClass")
    public ResponseEntity<result> selectByTeacherGetClass(@RequestParam(required = false,defaultValue = "0") Integer id){
        List<ClassT> teacherList = classservice.selectByTeacherGetClass(id);
        return ResultUtil.success(200,"获取成功", teacherList);
    }

    @deanLoginIntercept
    @GetMapping("/selectByClassIdTeacherIdGetCourse")
    public ResponseEntity<result> selectByClassIdTeacherIdGetCourse(@RequestParam(required = false,defaultValue = "0") Integer id,
                                                           @RequestParam(required = false,defaultValue = "0") Integer teacherId){

        List<course> teacherList = courseservice.selectByClassIdTeacherIdGetCourse(id, teacherId);
        return ResultUtil.success(200,"获取成功", teacherList);
    }
    @deanLoginIntercept
    @GetMapping("/selectTeacher")
    public ResponseEntity<result> selectTeacher(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                @RequestParam(required = false,defaultValue = "0") Integer acId,
                                                @RequestParam(required = false,defaultValue = "0") String SelectData){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            deanId = 0;
        }
        PageInfo<teacher> studentData = deanservice.selectTeacher(page, pageSize, acId, SelectData, deanId);
        return ResultUtil.success(200,"获取成功", studentData);

    }
    @deanLoginIntercept
    @GetMapping("/selectCourse")
    public ResponseEntity<result> selectCourse(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                @RequestParam(required = false,defaultValue = "0") Integer acId,
                                                @RequestParam(required = false,defaultValue = "0") String SelectData){
        Integer deanId = 0;
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        }

        PageInfo<course> studentData = deanservice.selectCourse(page, pageSize, acId, SelectData, deanId);
        return ResultUtil.success(200,"获取成功", studentData);

    }

    @deanLoginIntercept
    @GetMapping("/getClass")
    public ResponseEntity<result> getsClass(@RequestParam(required = false,defaultValue = "1") Integer page,
                                            @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                            @RequestParam(required = false,defaultValue = "0") String specialtyName,
                                            @RequestParam(required = false,defaultValue = "0") Integer academyId,
                                            @RequestParam(required = false,defaultValue = "0") String Select
    ){
        Integer deanId = 0;
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        }
        PageInfo<ClassT> data = classservice.selectClass(page, pageSize, specialtyName, academyId, Select, deanId);
        return ResultUtil.success(200,"获取成功", data);
    }

    @deanLoginIntercept
    @GetMapping("/selectCourseData")
    public ResponseEntity<result> selectCourseData(@RequestParam Integer id){
        course studentData = deanservice.selectCourseData(id);
        return ResultUtil.success(200,"获取成功", studentData);

    }
    @deanLoginIntercept
    @GetMapping("/selectByIdTeacher")
    public ResponseEntity<result> selectByIdTeacher(@RequestParam Integer Id){
        teacher Data = deanservice.selectByIdTeacher(Id);
        return ResultUtil.success(200,"获取成功", Data);
    }
    @StudentLoginIntercept
    @deanLoginIntercept
    @GetMapping("/selectAcademyOrClass")
    public ResponseEntity<result> selectAcademyOrClass(@RequestParam(required = false,defaultValue = "1") String Academy){
        Integer deanId = 0;
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        }
        List<Map> studentData = deanservice.selectAcademyOrClass(Academy, deanId);
        return ResultUtil.success(201,"获取成功", studentData);
    }
    @deanLoginIntercept
    @GetMapping("/selectAcademyOrSpecialty")
    public ResponseEntity<result> selectAcademyOrSpecialty(@RequestParam(required = false,defaultValue = "1") String Academy){
        Integer deanId = 0;
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        }
        List<Map> studentData = classservice.selectAcademyOrSpecialty(Academy, deanId);
        return ResultUtil.success(201,"获取成功", studentData);
    }
    @deanLoginIntercept
    @GetMapping("/selectByIdStudent")
    public ResponseEntity<result> selectByIdStudent(@RequestParam Integer StudentId){
        student studentData = deanservice.selectByIdStudent(StudentId);
        return ResultUtil.success(201,"获取成功", studentData);
    }

    @deanLoginIntercept
    @GetMapping("/selectClass")
    public ResponseEntity<result> selectClass(@RequestParam(required = false,defaultValue = "0") Integer academyId){
        List<ClassT> studentData = deanservice.selectClass(academyId);
        return ResultUtil.success(201,"获取成功", studentData);
    }

    @deanLoginIntercept
    @PostMapping ("/StudentSetClass")
    public ResponseEntity<result> StudentSetClass(@RequestBody Map<String, String> data){
        Integer id = Integer.valueOf(data.get("id"));
        Integer ClassId = Integer.valueOf(data.get("classId"));
        Integer start = deanservice.StudentSetClass(id, ClassId);

        if (start>0) {
            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "班级不存在");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/teacherSetAcademy")
    public ResponseEntity<result> teacherSetAcademy(@RequestBody Map<String, String> data){
        Integer id = Integer.valueOf(data.get("id"));
        Integer AcademyId = Integer.valueOf(data.get("academyId"));
        Integer start = deanservice.StudentSetAcademy(id, AcademyId);

        if (start>0) {
            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "班级不存在");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/editStudent")
    public ResponseEntity<result> editStudent(@RequestBody student data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if (ThreadLocalMap.get("userType").toString().equals("3")){

            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "修改了名字为" + data.getName() + "的学生信息";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }
        Integer start = deanservice.editStudent(data);
        if (start>0) {

            return ResultUtil.success(201,"修改成功", "");
        }  else {
            return ResultUtil.error(401, "修改失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/editTeacher")
    public ResponseEntity<result> editTeacher(@RequestBody teacher data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "修改了名字为" + data.getName() + "的老师信息";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }
        Integer start = deanservice.editTeacher(data);
        if (start>0) {

            return ResultUtil.success(201,"修改成功", "");
        }  else {
            return ResultUtil.error(401, "修改失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/editCourse")
    public ResponseEntity<result> editCourse(@RequestBody course data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "修改名字为" + data.getName() + "的课程";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }
        Integer start = deanservice.editCourse(data);
        if (start>0) {

            return ResultUtil.success(201,"修改成功", "");
        }  else {
            return ResultUtil.error(401, "修改失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/deleteTeacher")
    public ResponseEntity<result> deleteTeacher(@RequestBody teacher data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            teacher teacherData = deanservice.selectByIdTeacher(data.getId());
            if (!adminAuthority.checkAuthority(deanId,teacherData.getAcademyId(), adminservice, "delete"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "删除名字为" + teacherData.getName() + "的教师";
                adminAuthority.writeDeanLog(adminservice ,deanId,teacherData.getAcademyId(),log);
            }
        }
        Integer start = deanservice.deleteTeacher(data.getId());

        if (start>0) {
            return ResultUtil.success(201,"删除成功", "");
        }  else {
            return ResultUtil.error(401, "删除失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/deleteCourse")
    public ResponseEntity<result> deleteCourse(@RequestBody course data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            course courseData = deanservice.selectCourseData(data.getId());

            if (!adminAuthority.checkAuthority(deanId,courseData.getAcademyId() , adminservice, "delete"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "删除名字为" + courseData.getName() + "的课程";
                adminAuthority.writeDeanLog(adminservice ,deanId,courseData.getAcademyId(),log);
            }
        }
        Integer start = deanservice.deleteCourse(data.getId());

        if (start>0) {
            return ResultUtil.success(201,"删除成功", "");
        }  else {
            return ResultUtil.error(401, "删除失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping ("/deleteStudent")
    public ResponseEntity<result> deleteStudent(@RequestBody student data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            student studentData = deanservice.selectByIdStudent(data.getId());
            if (!adminAuthority.checkAuthority(deanId,studentData.getAcademyId() , adminservice, "delete"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "删除名字为" + studentData.getName() + "的学生";
                adminAuthority.writeDeanLog(adminservice ,deanId,studentData.getAcademyId(),log);
            }
        }
        Integer start = deanservice.deleteStudent(data.getId());

        if (start>0) {
            return ResultUtil.success(201,"删除成功", "");
        }  else {
            return ResultUtil.error(401, "删除失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @PostMapping("/resetStudentPassword")
    public ResponseEntity<result> resetStudentPassword(@RequestBody Map<String, Integer> data){
        Integer studentId = data.get("id");
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            student teacherData = deanservice.selectByIdStudent(studentId);
            if (!adminAuthority.checkAuthority(deanId,teacherData.getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "重置名字为" + teacherData.getName() + "的学生登录密码";
                adminAuthority.writeDeanLog(adminservice ,deanId,teacherData.getAcademyId(),log);
            }
        }
        Integer studentData = loginservice.resetStudentPassword(studentId);
        return ResultUtil.success(200,"重置成功", "");
    }

    @deanLoginIntercept
    @PostMapping("/resetTeacherPassword")
    public ResponseEntity<result> resetTeacherPassword(@RequestBody Map<String, Integer> data){
        Integer teacherId = data.get("id");
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            teacher teacherData = deanservice.selectByIdTeacher(teacherId);
            if (!adminAuthority.checkAuthority(deanId,teacherData.getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "重置名字为" + teacherData.getName() + "的老师登录密码";
                adminAuthority.writeDeanLog(adminservice ,deanId,teacherData.getAcademyId(),log);
            }
        }
        Integer studentData = loginservice.resetTeacherPassword(teacherId);
        return ResultUtil.success(200,"重置成功", "");
    }

    @deanLoginIntercept
    @GetMapping("/getDeanUserData")
    public ResponseEntity<result> getDeanUserData(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        dean Data = adminservice.GetDeanData(deanId);
        return ResultUtil.success(200,"重置成功", Data);
    }

    @deanLoginIntercept
    @PostMapping ("/editDeanUserData")
    public ResponseEntity<result> editDeanUserData(@RequestBody dean data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        data.setId(deanId);
        Integer Data = deanservice.editDeanUserData(data);
        return ResultUtil.success(200,"修改成功", Data);
    }
}
