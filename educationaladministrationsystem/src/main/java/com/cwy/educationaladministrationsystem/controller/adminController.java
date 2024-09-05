package com.cwy.educationaladministrationsystem.controller;

import com.cwy.educationaladministrationsystem.intercept.adminLoginIntercept;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.adminService;
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
@RequestMapping("/adminApi")
public class adminController {

    @Autowired
    private adminService adminservice;

    @adminLoginIntercept
    @PostMapping("/AddDeanUser")
    public ResponseEntity<result> AddDeanUser(@RequestBody dean data){
        Integer state = adminservice.AddDeanUser(data);
        if (state == 1){
            return ResultUtil.success(200,"添加成功", state);
        }else {
            return ResultUtil.error(403,"已有该Id");
        }
    }

    @adminLoginIntercept
    @PostMapping("/AddAdminUser")
    public ResponseEntity<result> AddAdminUser(@RequestBody Map<String, String> data){
        dean userData = new dean();
        userData.setName(data.get("name"));
        userData.setPassword(data.get("password"));
        userData.setEmail(data.get("email"));
        userData.setPhone(data.get("phone"));

        Integer state = adminservice.AddAdminUser(userData);

        if (state == 1){
            return ResultUtil.success(200,"添加成功", state);
        }else {
            return ResultUtil.error(403,"已有该Id");
        }
    }

    @adminLoginIntercept
    @PostMapping("/addAcademy")
    public ResponseEntity<result> addAcademy(@RequestBody academy data){
        Integer start =  adminservice.addAcademy(data);
        if (start>0) {
            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该学院");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
//        return ResultUtil.error(201,"失败用户不存在数据库中");
    }

    @adminLoginIntercept
    @PostMapping("/addSpecialty")
    public ResponseEntity<result> addSpecialty(@RequestBody specialty data){
        Integer start =  adminservice.addSpecialty(data);
        if (start>0) {
            return ResultUtil.success(201,"插入成功", "");
        } else if (start==0) {
            return ResultUtil.error(401, "已有该专业");
        } else {
            return ResultUtil.error(401, "插入失败，请联系管理员");
        }
//        return ResultUtil.error(201,"失败用户不存在数据库中");
    }

    @adminLoginIntercept
    @PostMapping("/editSpecialty")
    public ResponseEntity<result> editSpecialty(@RequestBody specialty data){
        Integer state = adminservice.editSpecialty(data);
        return ResultUtil.success(200,"修改成功", state);
    }

    @adminLoginIntercept
    @PostMapping("/editAcademy")
    public ResponseEntity<result> editAcademy(@RequestBody academy data){
        Integer state = adminservice.editAcademy(data);
        return ResultUtil.success(200,"修改成功", state);
    }

    @adminLoginIntercept
    @PostMapping("/EditDeanUser")
    public ResponseEntity<result> EditDeanUser(@RequestBody dean data){
        Integer state = adminservice.EditDeanUser(data);
        return ResultUtil.success(200,"修改成功", state);
    }

    @adminLoginIntercept
    @PostMapping("/EditAdminUser")
    public ResponseEntity<result> EditAdminUser(@RequestBody dean data){
        Integer state = adminservice.EditAdminUser(data);
        return ResultUtil.success(200,"修改成功", state);
    }



    @adminLoginIntercept
    @PostMapping("/DeleteDeanUser")
    public ResponseEntity<result> DeleteDeanUser(@RequestBody dean data){
        Integer state = adminservice.DeleteDeanUser(data.getId());
        return ResultUtil.success(200,"删除成功", state);
    }

    @adminLoginIntercept
    @PostMapping("/DeleteAdminUser")
    public ResponseEntity<result> DeleteAdminUser(@RequestBody dean data){
        Integer state = adminservice.DeleteAdminUser(data.getId());
        return ResultUtil.success(200,"删除成功", state);
    }

    @adminLoginIntercept
    @PostMapping("/AddDeanAuthority")
    public ResponseEntity<result> AddDeanAuthority(@RequestBody Map<String, Object> data){
        List<Object> Authority = (List<Object>) data.get("AuthorityList");
        Integer deanId = Integer.valueOf(data.get("deanId").toString());
        Integer state = adminservice.AddDeanAuthority(deanId, Authority);
        return ResultUtil.success(200,"添加成功", state);
    }

    @adminLoginIntercept
    @GetMapping("/GetDeanUser")
    public ResponseEntity<result> GetTeacherCurriculum(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                       @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                       @RequestParam(required = false,defaultValue = "0") Integer acId){
        PageInfo<dean> deanInfo = adminservice.selectDean(page, pageSize, acId);
        return ResultUtil.success(200,"获取成功", deanInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetAdminUserData")
    public ResponseEntity<result> GetAdminUserData(){
        Map<String, Object> ThreadLocalMap = ThreadLocalApi.get();
        Integer Id = Integer.valueOf(ThreadLocalMap.get("uid").toString());
        dean Info = adminservice.GetByIdAdmin(Id);
        return ResultUtil.success(200,"获取成功", Info);
    }

    @adminLoginIntercept
    @GetMapping("/GetAdminUser")
    public ResponseEntity<result> GetAdminUser(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                       @RequestParam(required = false,defaultValue = "5") Integer pageSize){
        PageInfo<dean> adminInfo = adminservice.GetAdminUser(page, pageSize);
        return ResultUtil.success(200,"获取成功", adminInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetSpecialtyList")
    public ResponseEntity<result> GetSpecialtyList(@RequestParam(required = false,defaultValue = "1") Integer page,
                                                       @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                                       @RequestParam(required = false,defaultValue = "0") Integer acId,
                                                   @RequestParam(required = false,defaultValue = "0") String select){
        PageInfo<specialty> deanInfo = adminservice.GetSpecialtyList(page, pageSize, acId, select);
        return ResultUtil.success(200,"获取成功", deanInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetDeanLog")
    public ResponseEntity<result> GetDeanLog(@RequestParam(required = false,defaultValue = "0") Integer deanId,
                                             @RequestParam(required = false,defaultValue = "0") Integer academyId){
        List<String> deanInfo = adminservice.GetDeanLog(deanId, academyId);
        return ResultUtil.success(200,"获取成功", deanInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetAcademyList")
    public ResponseEntity<result> GetAcademyList(@RequestParam(required = false,defaultValue = "0") Integer page,
                                                 @RequestParam(required = false,defaultValue = "0") Integer pageSize,
                                                 @RequestParam(required = false,defaultValue = "0") String select){
        PageInfo<academy> academyInfo = adminservice.GetAcademyList(page, pageSize, select);
        return ResultUtil.success(200,"获取成功", academyInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetByIdAcademy")
    public ResponseEntity<result> GetByIdAcademy(@RequestParam(required = false,defaultValue = "0") Integer id){
        academy academyInfo = adminservice.GetByIdAcademy(id);
        return ResultUtil.success(200,"获取成功", academyInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetByIdAdmin")
    public ResponseEntity<result> GetByIdAdmin(@RequestParam(required = false,defaultValue = "0") Integer id){
        dean adminData = adminservice.GetByIdAdmin(id);
        return ResultUtil.success(200,"获取成功", adminData);
    }

    @adminLoginIntercept
    @GetMapping("/GetDeanData")
    public ResponseEntity<result> GetDeanData(@RequestParam(required = false,defaultValue = "0") Integer deanId){
        dean deanInfo = adminservice.GetDeanData(deanId);
        return ResultUtil.success(200,"获取成功", deanInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetDeanAcademyList")
    public ResponseEntity<result> GetDeanAcademyList(@RequestParam(required = false,defaultValue = "0") Integer deanId){
        List<academy> deanInfo = adminservice.GetDeanAcademyList(deanId);
        return ResultUtil.success(200,"获取成功", deanInfo);
    }

    @adminLoginIntercept
    @GetMapping("/GetAuthorityList")
    public ResponseEntity<result> GetAuthorityList(){
        List<Map<String, Object>> deanInfo = adminservice.GetAuthorityList();
        return ResultUtil.success(200,"获取成功", deanInfo);
    }

    @adminLoginIntercept
    @PostMapping("/DeleteAcademy")
    public ResponseEntity<result> DeleteAcademy(@RequestBody academy data){
        Integer state = adminservice.DeleteAcademy(data.getId());
        return ResultUtil.success(200,"删除成功", state);
    }

    @adminLoginIntercept
    @PostMapping("/DeleteSpecialty")
    public ResponseEntity<result> DeleteSpecialty(@RequestBody specialty data){
        Integer state = adminservice.DeleteSpecialty(data.getId());
        return ResultUtil.success(200,"删除成功", state);
    }


}
