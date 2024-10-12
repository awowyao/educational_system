package com.cwy.educationaladministrationsystem.controller;

import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.model.electiveCourse;
import com.cwy.educationaladministrationsystem.model.exam;
import com.cwy.educationaladministrationsystem.model.result;
import com.cwy.educationaladministrationsystem.model.student;
import com.cwy.educationaladministrationsystem.service.courseService;
import com.cwy.educationaladministrationsystem.util.ResultUtil;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/courseApi")
public class courseController {
    @Autowired
    private courseService courseservice;
    @deanLoginIntercept
    @PostMapping("/addElectiveCourse")
    public ResponseEntity<result> addElectiveCourse(@RequestBody electiveCourse data){
        Integer Static = courseservice.addElectiveCourse(data);
        if (Static == 1){
            return ResultUtil.success(200,"添加成功", "");
        }else {
            return ResultUtil.error(403,"该时间老师有课");
        }

    }
    @deanLoginIntercept
    @PostMapping("/editElectiveCourse")
    public ResponseEntity<result> editElectiveCourse(@RequestBody electiveCourse data){
        Integer Static = courseservice.editElectiveCourse(data);
        if (Static == 1){
            return ResultUtil.success(200,"修改成功", "");
        }else if (Static ==2){
            return ResultUtil.error(403,"该时间有学生有课");
        }else {
            return ResultUtil.error(403,"该时间老师有课");
        }

    }
    @deanLoginIntercept
    @GetMapping("/GetElectiveCourse")
    public ResponseEntity<result> GetElectiveCourse(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                @RequestParam(required = false,defaultValue = "0") String Select,
                                                @RequestParam(required = false,defaultValue = "0") Integer acId,
                                                @RequestParam(required = false,defaultValue = "0") Integer teacherId){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();

        Integer deanId = 0;
        if (ThreadLocalMap.get("userType").toString().equals("3")){
            deanId = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        }
        PageInfo<electiveCourse> studentData = courseservice.GetElectiveCourse(page, pageSize, acId, teacherId, Select, deanId);
        return ResultUtil.success(201,"获取成功", studentData);
    }
    @deanLoginIntercept
    @GetMapping("/GetElectiveCourseData")
    public ResponseEntity<result> GetElectiveCourseData(@RequestParam(required = false,defaultValue = "0") Integer id){
        electiveCourse studentData = courseservice.ByIdGetElectiveCourse(id);
        return ResultUtil.success(201,"获取成功", studentData);
    }


    @deanLoginIntercept
    @PostMapping("/deleteElectiveCourse")
    public ResponseEntity<result> deleteElectiveCourse(@RequestBody Map<String, Integer> reBody){
        Integer Static = courseservice.deleteElectiveCourse(reBody.get("id"));
        return ResultUtil.success(200,"删除成功", Static);
    }
}
