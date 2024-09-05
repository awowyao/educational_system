package com.cwy.educationaladministrationsystem.controller;
import com.cwy.educationaladministrationsystem.config.rabbitmqConfig;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.adminService;
import com.cwy.educationaladministrationsystem.service.classService;
import com.cwy.educationaladministrationsystem.service.deanService;
import com.cwy.educationaladministrationsystem.util.ResultUtil;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.cwy.educationaladministrationsystem.util.adminAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/classApi")
public class classController {
    @Autowired
    private classService classservice;
    @Autowired
    private deanService deanservice;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private adminService adminservice;
    @RequestMapping("/rabbtimqShenC")
    public ResponseEntity<result> test(){
        rabbitTemplate.convertAndSend(rabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email", "547779262@qq.com");
        return ResultUtil.error(401, "插入失败，请联系管理员");
    }

    @RequestMapping("/rabbtimqXiaof")
    public ResponseEntity<result> Xtest(){
        rabbitTemplate.convertAndSend(rabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email", "547779262@qq.com");
        return ResultUtil.error(401, "插入失败，请联系管理员");
    }

    @deanLoginIntercept
    @PostMapping ("/addClass")
    public ResponseEntity<result> addClass(@RequestBody ClassT data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            if (!adminAuthority.checkAuthority(deanId,data.getAcademyId() , adminservice, "add"))
                return ResultUtil.error(401, "无权限");
        }
        Integer start = deanservice.addClass(data);

        if (start>0) {
            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该班级");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
    }
    @deanLoginIntercept
    @PostMapping ("/deleteClass")
    public ResponseEntity<result> deleteClass(@RequestBody teacher data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            List<ClassT> classData = classservice.selectByIdClass(data.getId());
            if (!adminAuthority.checkAuthority(deanId,classData.get(0).getAcademyId() , adminservice, "delete"))
                return ResultUtil.error(401, "无权限");
        }

        Integer start = classservice.deleteClass(data.getId());
        if (start>0) {
            return ResultUtil.success(201,"删除成功", "");
        }  else {
            return ResultUtil.error(401, "删除失败，请联系管理员");
        }
    }

    @deanLoginIntercept
    @GetMapping("/GetClassCourse")
    public ResponseEntity<result> GetClassCourse(@RequestParam(required = false,defaultValue = "0") Integer ClassId){
        List<course> data = classservice.GetClassCourse(ClassId);
        return ResultUtil.success(200,"获取成功", data);
    }

    @deanLoginIntercept
    @GetMapping("/selectByIdClass")
    public ResponseEntity<result> selectByIdClass(@RequestParam(required = false,defaultValue = "0") Integer id){
        List<ClassT> data = classservice.selectByIdClass(id);
        return ResultUtil.success(200,"获取成功", data.get(0));
    }
    @deanLoginIntercept
    @GetMapping("/getSpecialty")
    public ResponseEntity<result> getSpecialty(@RequestParam(required = false,defaultValue = "0") Integer academyId){
        List<specialty> data = classservice.selectSpecialty(academyId);
        return ResultUtil.success(200,"获取成功", data);
    }
    @deanLoginIntercept
    @GetMapping("/ByAcademyIdGetCourse")
    public ResponseEntity<result> ByAcademyIdGetCourse(@RequestParam(required = false,defaultValue = "0") Integer academyId){
        List<course> data = classservice.ByAcademyIdGetCourse(academyId);
        return ResultUtil.success(200,"获取成功", data);
    }
    @deanLoginIntercept
    @GetMapping("/ByCourseIdGetTeacher")
    public ResponseEntity<result> ByCourseIdGetTeacher(@RequestParam(required = false,defaultValue = "0") Integer id){
        List<teacher> data = classservice.ByCourseIdGetTeacher(id);
        return ResultUtil.success(200,"获取成功", data);
    }

    @deanLoginIntercept
    @GetMapping("/GetCurriculum")
    public ResponseEntity<result> GetCurriculum(@RequestParam(required = false,defaultValue = "0") Integer id,
                                                @RequestParam(required = false,defaultValue = "0") Integer cid){
        if (id==0){
            return ResultUtil.error(401,"缺少id");
        }
        //        List<Map> CurriculumData = JSONObject.parseArray(serviceData.get(0).getCurriculumData(), Map.class);
//        serviceData.get(0).setCurriculumData();
        List<curriculum> studentData = classservice.GetCurriculum(id, cid, 3);
        return ResultUtil.success(200,"获取成功", studentData.get(0));
    }
    @deanLoginIntercept
    @GetMapping("/GetTeacherCurriculum")
    public ResponseEntity<result> GetTeacherCurriculum(@RequestParam(required = false,defaultValue = "0") Integer id,
                                                @RequestParam(required = false,defaultValue = "0") Integer cid){
        if (id==0){
            return ResultUtil.error(401,"缺少id");
        }
        //        List<Map> CurriculumData = JSONObject.parseArray(serviceData.get(0).getCurriculumData(), Map.class);
//        serviceData.get(0).setCurriculumData();
        List<curriculum> studentData = classservice.GetCurriculum(id, cid, 2);
        return ResultUtil.success(200,"获取成功", studentData.get(0));
    }
    @deanLoginIntercept
    @PostMapping ("/DeleteClassCourse")
    public ResponseEntity<result> DeleteClassCourse(@RequestBody Map<String,Integer> data){
        Integer classId = data.get("ClassId");
        Integer courseId = data.get("courseId");
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            List<ClassT> classData = classservice.selectByIdClass(classId);
            System.out.println(classId);
            if (!adminAuthority.checkAuthority(deanId,classData.get(0).getAcademyId() , adminservice, "delete"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，删除了" + classData.get(0).getName() + "班，ID为"+courseId +"的课程";
                adminAuthority.writeDeanLog(adminservice ,deanId,classData.get(0).getAcademyId(),log);
            }
        }
        Integer state = classservice.DeleteClassCourse(classId, courseId);
        return ResultUtil.success(200,"删除成功", state);
    }
    @deanLoginIntercept
    @PostMapping ("/editClass")
    public ResponseEntity<result> EditClass(@RequestBody ClassT data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            List<ClassT> classData = classservice.selectByIdClass(data.getId());
            if (!adminAuthority.checkAuthority(deanId,classData.get(0).getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，修改了" + classData.get(0).getName() + "班级的信息";
                adminAuthority.writeDeanLog(adminservice ,deanId,classData.get(0).getAcademyId(),log);
            }
        }
        if (data.getSpecialtyName() == ""){
            data.setSpecialtyName(null);
        }

        Integer studentData = classservice.EditClass(data);
        return ResultUtil.success(201,"获取成功", studentData);
    }
    @deanLoginIntercept
    @PostMapping ("/editCurriculum")
    public ResponseEntity<result> editCurriculum(@RequestBody Map<String, Object> data){
        Integer id = Integer.valueOf(data.get("id").toString());
        Integer cid = Integer.valueOf(data.get("cid").toString());
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            List<ClassT> classData = classservice.selectByIdClass(cid);

            if (!adminAuthority.checkAuthority(deanId,classData.get(0).getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，修改了" + classData.get(0).getName() + "班级的课程表";
                adminAuthority.writeDeanLog(adminservice ,deanId,classData.get(0).getAcademyId(),log);
            }
        }
        List<Map> CurriculumData = (List<Map>) data.get("curriculumData");
        Map oldCurData = (Map) data.get("oldCurData");
        Map newCurData = (Map) data.get("newCurData");

        Integer studentData = classservice.editCurriculum(id, CurriculumData, oldCurData, newCurData, cid);
        return ResultUtil.success(200,"获取成功", studentData);
    }
    @deanLoginIntercept
    @PostMapping ("/editTeacherCurriculum")
    public ResponseEntity<result> editTeacherCurriculum(@RequestBody Map<String, Object> data){
        Integer id = Integer.valueOf(data.get("id").toString());
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer cid = Integer.valueOf(data.get("cid").toString());
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            List<ClassT> classData = classservice.selectByIdClass(cid);
            if (!adminAuthority.checkAuthority(deanId,classData.get(0).getAcademyId() , adminservice, "edit"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，修改了" + classData.get(0).getName() + "班级的信息";
                adminAuthority.writeDeanLog(adminservice ,deanId,classData.get(0).getAcademyId(),log);
            }
        }
        List<Map> CurriculumData = (List<Map>) data.get("curriculumData");
        Map oldCurData = (Map) data.get("oldCurData");
        Map newCurData = (Map) data.get("newCurData");

        Integer studentData = classservice.editTeacherCurriculum(id, CurriculumData, oldCurData, newCurData, cid);
        return ResultUtil.success(200,"获取成功", studentData);
    }
    @deanLoginIntercept
    @PostMapping("/addClassCourse")
    public ResponseEntity<result> addClassCourse(@RequestBody Map<String, Integer> data){
        Integer classId = data.get("classId");
        Integer courseId = data.get("courseId");
        Integer teacherId = data.get("teacherId");
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            Integer deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
            List<ClassT> classData = classservice.selectByIdClass(classId);

            if (!adminAuthority.checkAuthority(deanId,classData.get(0).getAcademyId() , adminservice, "add"))
                return ResultUtil.error(401, "无权限");
            else {
                String deanName = ThreadLocalMap.get("userName").toString();
                String log = "id: "+ deanId +", 名字: " + deanName + "，添加了" + classData.get(0).getName() + "班级的课程";
                adminAuthority.writeDeanLog(adminservice ,deanId,classData.get(0).getAcademyId(),log);
            }
        }
        Integer studentData = classservice.addClassCourse(classId, courseId, teacherId);
        return ResultUtil.success(200,"获取成功", studentData);
    }

}
