package com.cwy.educationaladministrationsystem.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cwy.educationaladministrationsystem.mapper.adminMapper;
import com.cwy.educationaladministrationsystem.mapper.deanMapper;
import com.cwy.educationaladministrationsystem.model.academy;
import com.cwy.educationaladministrationsystem.model.dean;
import com.cwy.educationaladministrationsystem.model.deanAuthority;
import com.cwy.educationaladministrationsystem.model.specialty;
import com.cwy.educationaladministrationsystem.service.adminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class adminServiceImp implements adminService {
    private String Salt = "jiaowuguan";
    @Autowired
    private adminMapper adminmapper;

    @Autowired
    private deanMapper deanmapper;
    @Override
    public deanAuthority selectByIdGetAuthority(Integer deanId, Integer academyId) {
        return adminmapper.selectByIdGetAuthority(deanId, academyId);
    }

    @Override
    public void editDeanOperateLog(Integer deanId, Integer acId, String log) {
        adminmapper.editDeanOperateLog(deanId, acId, log);
    }

    @Override
    public PageInfo<dean> selectDean(Integer page, Integer pageSize, Integer acId) {
        PageHelper.startPage(page, pageSize);
        List<dean> deanList = adminmapper.selectDean(acId);
        PageInfo<dean> deanInfo = new PageInfo<>(deanList);
        return deanInfo;
    }

    @Override
    public List<Map<String, Object>> GetAuthorityList() {
        List<academy> academyList = deanmapper.selectAcademy("1", 0);
        List<Map<String, Object>> RList = new ArrayList<>();
        for (int i=0;i<academyList.size();i++) {
            Map<String, Object> acMap = new HashMap<>();
            acMap.put("id", academyList.get(i).getId());
            acMap.put("label", academyList.get(i).getName());
            List<Map<String, String>> childrenList = new ArrayList<>();
            Map<String, String> addMap = new HashMap<>();
            addMap.put("id", academyList.get(i).getId().toString()+":add");
            addMap.put("label", "增加");
            Map<String, String> editMap = new HashMap<>();
            editMap.put("id", academyList.get(i).getId().toString()+":edit");
            editMap.put("label", "编辑");
            Map<String, String> deleteMap = new HashMap<>();
            deleteMap.put("id", academyList.get(i).getId().toString()+":delete");
            deleteMap.put("label", "删除");
            childrenList.add(addMap);
            childrenList.add(editMap);
            childrenList.add(deleteMap);
            acMap.put("children",childrenList);
            RList.add(acMap);
        }
        return RList;
    }

    @Override
    public Integer AddDeanUser(dean data) {
        String Password = DigestUtils.md5DigestAsHex("123456".getBytes());
        Password = data.getId() + Salt + Password;
        Password = DigestUtils.md5DigestAsHex(Password.getBytes());
        data.setPassword(Password);
        Date date = new Date();
        data.setUpdateTime(date);
        data.setCreateTime(date);
        try {
            adminmapper.AddDeanUser(data);
        } catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return 0;
            }else {
                return -1;
            }
        }
        return 1;
    }

    @Override
    public Integer AddDeanAuthority(Integer deanId, List<Object> authority) {
        List<Map<String, Object>> AitList = new ArrayList<>();
        for(int i=0; i<authority.size();i++){
            List<String> AuthorityList = List.of(authority.get(i).toString().split(":"));
            if (AuthorityList.size()>1) {

                Integer acId = Integer.valueOf(AuthorityList.get(0));
                String Authority = AuthorityList.get(1);
                if (AitList.size() == 0){
                    Map<String, Object> Ait = new HashMap<>();
                    List<String> AList = new ArrayList<>();
                    AList.add(Authority);
                    Ait.put("acId", acId);
                    Ait.put("auList", AList);
                    AitList.add(Ait);
                }else {
                    Integer state = 0;
                    for (int j=0; j<AitList.size();j++){
                        if (AitList.get(j).get("acId").toString().equals(acId.toString())){
                            List<String> AList = (List<String>) AitList.get(j).get("auList");
                            AList.add(Authority);
                            AitList.get(j).replace("auList", AList);
                            state =1;
                            break;
                        }
                    }
                    if (state == 0){
                        Map<String, Object> Ait = new HashMap<>();
                        List<String> AList = new ArrayList<>();
                        AList.add(Authority);
                        Ait.put("acId", acId);
                        Ait.put("auList", AList);
                        AitList.add(Ait);
                    }
                }
            }
        }
        for (Map<String, Object> o :AitList) {
            Integer acId = Integer.valueOf(o.get("acId").toString());
            List<String> AuthorityL = (List<String>) o.get("auList");
            String AuthorityS = JSON.toJSONString(AuthorityL);
            adminmapper.AddDeanAuthority(deanId, acId, AuthorityS);
        }


        return 1;
    }

    @Override
    public dean GetDeanData(Integer deanId) {
        dean deanData = adminmapper.GetDeanData(deanId);
        List<deanAuthority> deanAuthorityList =  adminmapper.selectDeanAuthority(deanId);
        List<Object> A = new ArrayList<>();
        for (deanAuthority i :deanAuthorityList) {
            List<Object> Authority = JSONObject.parseArray(i.getAuthority(), Object.class);
            for (Object a :Authority) {
                A.add(i.getAcademyId().toString()+":"+a);
            }
        }
        deanData.setAuthorityList(A);
        return deanData;
    }

    @Override
    public Integer EditDeanUser(dean data) {
        Date date = new Date();
        List<Object> AList = data.getAuthorityList();
        adminmapper.deleteDeanA(data.getId());
        AddDeanAuthority(data.getId(), AList);
        data.setUpdateTime(date);
        return adminmapper.EditDeanUser(data);
    }

    @Override
    public Integer DeleteDeanUser(Integer id) {
        return adminmapper.DeleteDeanUser(id);
    }

    @Override
    public List<String> GetDeanLog(Integer deanId, Integer academyId) {
        deanAuthority data = adminmapper.selectByIdGetAuthority(deanId, academyId);
        List<String> OperateLog = JSONObject.parseArray(data.getOperateLog(), String.class);
        return OperateLog;
    }

    @Override
    public List<academy> GetDeanAcademyList(Integer deanId) {
        return adminmapper.GetDeanAcademyList(deanId);
    }

    @Override
    public PageInfo<academy> GetAcademyList(Integer page, Integer pageSize, String select) {
        PageHelper.startPage(page, pageSize);
        List<academy> academyList = adminmapper.GetAcademyList(select);
        PageInfo<academy> academyInfo = new PageInfo<>(academyList);
        return academyInfo;
    }

    @Override
    public Integer addAcademy(academy data) {
        data.setTeacherNumber(0);
        Date date = new Date();
        data.setCreate_time(date);
        data.setUpdate_time(date);
        try {
            adminmapper.addAcademy(data);
        } catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return 0;
            }else {
                return -1;
            }
        }
        return 1;

    }

    @Override
    public Integer DeleteAcademy(Integer id) {
        return adminmapper.DeleteAcademy(id);
    }

    @Override
    public Integer editAcademy(academy data) {
        Date date = new Date();
        data.setUpdate_time(date);
        return adminmapper.editAcademy(data);
    }

    @Override
    public academy GetByIdAcademy(Integer id) {
        return adminmapper.GetByIdAcademy(id);
    }

    @Override
    public PageInfo<specialty> GetSpecialtyList(Integer page, Integer pageSize, Integer acId, String select) {
        PageHelper.startPage(page, pageSize);
        List<specialty> specialtyList = adminmapper.GetSpecialtyList(acId, select);
        PageInfo<specialty> specialtyPageInfo = new PageInfo<>(specialtyList);
        return specialtyPageInfo;
    }

    @Override
    public Integer addSpecialty(specialty data) {
        Date date = new Date();
        data.setUpdate_time(date);
        data.setCreate_time(date);
        adminmapper.addSpecialty(data);
        return 1;
    }

    @Override
    public Integer editSpecialty(specialty data) {
        Date date = new Date();
        data.setUpdate_time(date);
        return adminmapper.editSpecialty(data);
    }

    @Override
    public Integer DeleteSpecialty(Integer id) {
        return adminmapper.DeleteSpecialty(id);
    }

    @Override
    public PageInfo<dean> GetAdminUser(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<dean> adminList = adminmapper.GetAdminUser();
        PageInfo<dean> adminPageInfo = new PageInfo<>(adminList);
        return adminPageInfo;
    }

    @Override
    public Integer AddAdminUser(dean data) {
        Date date = new Date();
        String Password = data.getPassword();
        Password = data.getName() + Salt + Password;
        Password = DigestUtils.md5DigestAsHex(Password.getBytes());
        data.setPassword(Password);
        data.setUpdateTime(date);
        data.setCreateTime(date);
        return adminmapper.AddAdminUser(data);
    }

    @Override
    public Integer EditAdminUser(dean data) {
        Date date= new Date();
        data.setUpdateTime(date);
        return adminmapper.EditAdminUser(data);
    }

    @Override
    public dean GetByIdAdmin(Integer id) {
        return adminmapper.GetByIdAdmin(id);
    }

    @Override
    public Integer DeleteAdminUser(Integer id) {
        return adminmapper.DeleteAdminUser(id);
    }


}
