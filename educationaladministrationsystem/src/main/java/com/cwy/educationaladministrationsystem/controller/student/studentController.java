package com.cwy.educationaladministrationsystem.controller.student;


import com.cwy.educationaladministrationsystem.intercept.StudentLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.teacherLoginIntercept;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.*;
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
@RequestMapping("/studentApi")
public class studentController {
    @Autowired
    private classService classservice;
    @Autowired
    private deanService deanservice;
    @Autowired
    private loginService loginservice;
    @Autowired
    private courseService courseservice;
    @Autowired
    private studentService studentservice;

    @StudentLoginIntercept
    @teacherLoginIntercept
    @GetMapping("/GetStudentCurriculum")
    public ResponseEntity<result> GetTeacherCurriculum(@RequestParam(required = false,defaultValue = "0") Integer sId,
                                                       @RequestParam(required = false,defaultValue = "0") Integer cid){

        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer id = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if (!ThreadLocalMap.get("userType").toString().equals("1")){
            id = sId;
        }
        student studentD = deanservice.selectByIdStudent(id);
        if (id==0){
            return ResultUtil.error(401,"缺少id");
        }


        List<curriculum> studentData = classservice.GetCurriculum(id, studentD.getCurriculumId(), 1);
        return ResultUtil.success(200,"获取成功", studentData.get(0));
    }


    @StudentLoginIntercept
    @GetMapping("/GetStudentScores")
    public ResponseEntity<result> GetStudentScores(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                   @RequestParam(required = false,defaultValue = "0") Integer pageSize,
                                                   @RequestParam(required = false,defaultValue = "0") String select,
                                                   @RequestParam(required = false,defaultValue = "0") String year,
                                                   @RequestParam(required = false,defaultValue = "0") Integer studentId){


        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer StudentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            StudentId = studentId;
        }
        System.out.println(StudentId);
        PageInfo<course> studentData = studentservice.GetStudentScores(page,pageSize,StudentId,select, year);
        return ResultUtil.success(200,"获取成功", studentData);
    }

    @StudentLoginIntercept
    @PostMapping("/ComplaintCourse")
    public ResponseEntity<result> ComplaintCourse(@RequestBody Map data){
        Integer CourseId = (Integer) data.get("CourseId");
        Integer seId = (Integer) data.get("seId");
        Integer teacherId = (Integer) data.get("teacherId");
        Integer state = studentservice.ComplaintCourse(CourseId,teacherId,seId);

        if (state == 0) {
            return ResultUtil.error(403,"不能重复申述");
        }
        return ResultUtil.success(200,"获取成功", state);
    }

    @StudentLoginIntercept
    @GetMapping("/selectByIdStudent")
    public ResponseEntity<result> selectByIdStudent(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer id = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        student studentD = deanservice.selectByIdStudent(id);
        return ResultUtil.success(200,"获取成功", studentD);
    }
    @StudentLoginIntercept
    @GetMapping("/GetComplaintTable")
    public ResponseEntity<result> GetComplaintTable(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                    @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                    @RequestParam(required = false,defaultValue = "0") String select,
                                                    @RequestParam(required = false,defaultValue = "0") Integer studentId){

        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer StudentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            StudentId = studentId;
        }

        PageInfo<Map> state = studentservice.GetComplaintTable(StudentId,page,pageSize,select);
        return ResultUtil.success(200,"获取成功", state);
    }

    @StudentLoginIntercept
    @GetMapping("/getElectiveCourse")
    public ResponseEntity<result> getElectiveCourse(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                    @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                    @RequestParam(required = false,defaultValue = "0") String select,
                                                    @RequestParam(required = false,defaultValue = "0") Integer academyId,
                                                    @RequestParam(required = false,defaultValue = "0") Integer teacherId,
                                                    @RequestParam(required = false,defaultValue = "0") Integer studentId){

        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer StudentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            StudentId = studentId;
        }
        PageInfo<electiveCourse> data = courseservice.StudentGetElectiveCourse(page, pageSize, select, academyId, teacherId, StudentId);
        return ResultUtil.success(200,"获取成功", data);
    }

    @StudentLoginIntercept
    @GetMapping("/GetChooseCourseList")
    public ResponseEntity<result> GetChooseCourseList(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                      @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                      @RequestParam(required = false,defaultValue = "0") String Select,
                                                      @RequestParam(required = false,defaultValue = "0") Integer acId,
                                                      @RequestParam(required = false,defaultValue = "0") Integer teacherId,
                                                      @RequestParam(required = false,defaultValue = "0") Integer studentId){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer StudentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());

        if(ThreadLocalMap.get("userType").toString().equals("4")){
            StudentId = studentId;
        }
        PageInfo<electiveCourse> data = courseservice.ByStudentIdGetElectiveCourse(page, pageSize, StudentId, acId,teacherId, Select);
        return ResultUtil.success(201,"获取成功", data);
    }
    @StudentLoginIntercept
    @PostMapping("/chooseCourse")
    public ResponseEntity<result> chooseCourse(@RequestBody Map<String, Integer> data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer StudentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        Integer cid = data.get("cid");
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            StudentId = data.get("studentId");
        }
        Integer state = courseservice.chooseCourse(StudentId,cid);
        if (state == 1){
            return ResultUtil.success(200,"选择成功", state);
        }else if (state == 0) {
            return ResultUtil.error(403,"该时间段有课");
        }else if (state == -1){
            return ResultUtil.error(403,"课程已满");
        }else {
            return ResultUtil.error(403,"未知错误");
        }
    }


    @StudentLoginIntercept
    @PostMapping("/cancelCourse")
    public ResponseEntity<result> CancelClass(@RequestBody Map<String, Integer> data){
        Integer cid = data.get("cid");
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer studentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        if(ThreadLocalMap.get("userType").toString().equals("4")){
            studentId = data.get("studenId");
        }
        Integer state = courseservice.CancelClass(cid, studentId);
        return ResultUtil.success(200,"取消成功", state);
    }

    @StudentLoginIntercept
    @PostMapping("/editStudentUser")
    public ResponseEntity<result> editStudentUser(@RequestBody student data){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer studentId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        data.setId(studentId);
        Integer state = deanservice.editStudent(data);
        return ResultUtil.success(200,"取消成功", state);
    }


}
