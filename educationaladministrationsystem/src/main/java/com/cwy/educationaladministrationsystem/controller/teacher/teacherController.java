package com.cwy.educationaladministrationsystem.controller.teacher;

import com.cwy.educationaladministrationsystem.intercept.teacherLoginIntercept;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.classService;
import com.cwy.educationaladministrationsystem.service.deanService;
import com.cwy.educationaladministrationsystem.service.studentService;
import com.cwy.educationaladministrationsystem.util.ResultUtil;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/teacherApi")
public class teacherController {
    @Autowired
    private studentService studentservice;
    @Autowired
    private deanService deanservice;
    @Autowired
    private classService classservice;
    @teacherLoginIntercept
    @GetMapping("/GetByTeacherStudent")
    public ResponseEntity<result> GetByTeacherStudent(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                      @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false,defaultValue = "0") String Select,
                                                      @RequestParam(required = false,defaultValue = "0") Integer classId,
                                                      @RequestParam(required = false,defaultValue = "0") Integer teacherId){

        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer TeacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            TeacherId = teacherId;
        }
        PageInfo<student> studentList = studentservice.GetByTeacherIdStudent(page,pageSize,TeacherId, classId, Select);
        return ResultUtil.success(200,"获取成功", studentList);
    }

    @teacherLoginIntercept
    @GetMapping("/GetByExamStudent")
    public ResponseEntity<result> GetByExamStudent(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                      @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false,defaultValue = "0") String Select,
                                                      @RequestParam(required = false,defaultValue = "0") Integer classId,
                                                        @RequestParam(required = false,defaultValue = "0") Integer courseId,
                                                        @RequestParam(required = false,defaultValue = "0") Integer teacherId){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer TeacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            TeacherId = teacherId;
        }
        PageInfo<student> studentList = studentservice.GetByExamStudent(page,pageSize,TeacherId, classId, Select, courseId);
        return ResultUtil.success(200,"获取成功", studentList);
    }

    @teacherLoginIntercept
    @GetMapping("/GetByTeacherClass")
    public ResponseEntity<result> GetByTeacherClass(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer teacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        List<ClassT> studentList = classservice.GetByTeacherClass(teacherId);
        return ResultUtil.success(200,"获取成功", studentList);
    }

    @teacherLoginIntercept
    @GetMapping("/GetStudentComplaint")
    public ResponseEntity<result> GetStudentComplaint(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                   @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                   @RequestParam(required = false,defaultValue = "0") String Select,
                                                   @RequestParam(required = false,defaultValue = "0") Integer classId,
                                                      @RequestParam(required = false,defaultValue = "0") Integer  courseId,
                                                      @RequestParam(required = false,defaultValue = "0") Integer teacherId){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer TeacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            TeacherId = teacherId;
        }
        PageInfo<student> studentList = studentservice.GetStudentComplaint(page,pageSize,TeacherId, classId, Select, courseId);
        return ResultUtil.success(200,"获取成功", studentList);
    }
    @teacherLoginIntercept
    @PostMapping("/editStudentScores")
    public ResponseEntity<result> editStudentScores(@RequestBody Map<String, Integer> data){
        Integer studentExamId = data.get("id");
        Integer scores = data.get("scores");
        Integer def = data.get("def");
        Integer STATE = studentservice.editStudentScores(studentExamId, scores, def);
        return ResultUtil.success(200,"提交成功", STATE);
    }

    @teacherLoginIntercept
    @PostMapping("/DeleteStudentComplaint")
    public ResponseEntity<result> DeleteStudentComplaint(@RequestBody Map<String, Integer> data){
        Integer studentExamId = data.get("id");
//        Integer scores = data.get("scores");
        Integer STATE = studentservice.DeleteStudentComplaint(studentExamId);
        return ResultUtil.success(200,"提交成功", STATE);
    }

    @teacherLoginIntercept
    @GetMapping("/GetTeacherCurriculum")
    public ResponseEntity<result> GetTeacherCurriculum(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer teacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        teacher teacherData = deanservice.selectByIdTeacher(teacherId);
        List<curriculum> studentData = classservice.GetCurriculum(teacherId, teacherData.getCurriculumId(), 2);
        return ResultUtil.success(200,"获取成功", studentData.get(0));
    }

    @teacherLoginIntercept
    @GetMapping("/GetClassAndCourse")
    public ResponseEntity<result> GetClassAndCourse(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer teacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        List<Map<String,Object>> Data = classservice.GetClassAndCourse(teacherId);

        return ResultUtil.success(200,"获取成功", Data);
    }

    @teacherLoginIntercept
    @GetMapping("/GetTeacherUserData")
    public ResponseEntity<result> GetTeacherUserData(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer teacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        teacher Data = deanservice.selectByIdTeacher(teacherId);
        return ResultUtil.success(200,"获取成功", Data);
    }
    @teacherLoginIntercept
    @PostMapping ("/editTeacherUserData")
    public ResponseEntity<result> editTeacherUserData(@RequestBody teacher data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer teacherId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        data.setId(teacherId);
        Integer Data = deanservice.editTeacher(data);
        return ResultUtil.success(200,"获取成功", Data);
    }
}
