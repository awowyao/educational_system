package com.cwy.educationaladministrationsystem.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cwy.educationaladministrationsystem.model.deanAuthority;
import com.cwy.educationaladministrationsystem.service.adminService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class adminAuthority {


    public static boolean checkAuthority(Integer deanId, Integer acId, adminService adminservice, String path) {
        deanAuthority AuthorityData = adminservice.selectByIdGetAuthority(deanId, acId);
        if (AuthorityData == null) {
            return false;
        }
        List<String> Authority = JSONObject.parseArray(AuthorityData.getAuthority(), String.class);
        Set<String> set = new HashSet<String>(Authority);
        return set.contains(path);
    }

    public static boolean writeDeanLog(adminService adminservice,Integer deanId,Integer acId, String text) {
        deanAuthority AuthorityData = adminservice.selectByIdGetAuthority(deanId, acId);
        List<String> OperateLog = JSONObject.parseArray(AuthorityData.getOperateLog(), String.class);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = formatter.format(date);
        OperateLog.add(nowTime +": "+text);
        String Log = JSON.toJSONString(OperateLog);
        adminservice.editDeanOperateLog(deanId, acId, Log);

        return true;
    }
}
