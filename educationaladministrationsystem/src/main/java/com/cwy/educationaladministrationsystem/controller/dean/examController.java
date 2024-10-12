package com.cwy.educationaladministrationsystem.controller.dean;

import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.adminService;
import com.cwy.educationaladministrationsystem.service.dean.examService;
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
@RequestMapping("/examApi")
public class examController {
    @Autowired
    private examService examservice;

    @Autowired
    private adminService adminservice;
    @deanLoginIntercept
    @PostMapping("/addExam")
    public ResponseEntity<result> addExam(@RequestBody exam data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "add"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，添加了名字为：" + data.getName() + "的考试";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }


        Integer studentData = examservice.addExam(data);
        return ResultUtil.success(200,"添加成功", "");
    }
    @deanLoginIntercept
    @PostMapping("/editExam")
    public ResponseEntity<result> editExam(@RequestBody exam data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            exam examData = examservice.selectByIdExam(data.getId());
            if (!adminAuthority.checkAuthority(deanId,examData.getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，修改了名字为：" + examData.getName() + "的考试信息";
                adminAuthority.writeDeanLog(adminservice ,deanId,data.getAcademyId(),log);
            }
        }
        Integer studentData = examservice.editExam(data);
        return ResultUtil.success(200,"修改成功", "");
    }
    @deanLoginIntercept
    @GetMapping("/selectExamList")
    public ResponseEntity<result> selectClass(@RequestParam(required = false,defaultValue = "1") Integer page,
                                              @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                              @RequestParam(required = false,defaultValue = "0") Integer acId,
                                              @RequestParam(required = false,defaultValue = "0") String specialtyName,
                                              @RequestParam(required = false,defaultValue = "0") String SelectData){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer deanId = 0;
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        }
        PageInfo<exam> studentData = examservice.selectExamList(page, pageSize, acId, specialtyName, SelectData, deanId);
        return ResultUtil.success(200,"获取成功", studentData);
    }
    @deanLoginIntercept
    @GetMapping("/selectByIdExam")
    public ResponseEntity<result> selectByIdExam(@RequestParam(required = false,defaultValue = "0") Integer id){
        exam studentData = examservice.selectByIdExam(id);
        return ResultUtil.success(200,"获取成功", studentData);
    }
    @deanLoginIntercept
    @GetMapping("/BySpecialtyGetCourse")
    public ResponseEntity<result> selectClass(@RequestParam(required = false,defaultValue = "0") String Specialty){
        List<course> studentData = examservice.BySpecialtyGetCourse(Specialty);
        return ResultUtil.success(200,"获取成功", studentData);
    }
    @deanLoginIntercept
    @PostMapping("/deleteExam")
    public ResponseEntity<result> deleteExam(@RequestBody exam data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            exam examData = examservice.selectByIdExam(data.getId());
            if (!adminAuthority.checkAuthority(deanId,examData.getAcademyId() , adminservice, "delete"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，删除了名字为：" + examData.getName() + "的考试";
                adminAuthority.writeDeanLog(adminservice ,deanId,examData.getAcademyId(),log);
            }
        }
        Integer studentData = examservice.deleteExam(data.getId());
        return ResultUtil.success(200,"删除成功", "");
    }


}
