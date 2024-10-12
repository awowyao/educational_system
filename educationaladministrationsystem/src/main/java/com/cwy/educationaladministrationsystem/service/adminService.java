package com.cwy.educationaladministrationsystem.service;

import com.cwy.educationaladministrationsystem.model.academy;
import com.cwy.educationaladministrationsystem.model.dean;
import com.cwy.educationaladministrationsystem.model.deanAuthority;
import com.cwy.educationaladministrationsystem.model.specialty;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface adminService {
    deanAuthority selectByIdGetAuthority(Integer deanId, Integer academyId);

    void editDeanOperateLog(Integer deanId, Integer acId, String log);

    PageInfo<dean> selectDean(Integer page, Integer pageSize, Integer acId);


    List<Map<String, Object>> GetAuthorityList();

    Integer AddDeanUser(dean data);

    Integer AddDeanAuthority(Integer deanId, List<Object>  authority);

    dean GetDeanData(Integer deanId);

    Integer EditDeanUser(dean data);

    Integer DeleteDeanUser(Integer id);

    List<String> GetDeanLog(Integer deanId, Integer academyId);

    List<academy> GetDeanAcademyList(Integer deanId);

    PageInfo<academy> GetAcademyList(Integer page, Integer pageSize, String select);

    Integer addAcademy(academy data);

    Integer DeleteAcademy(Integer id);

    Integer editAcademy(academy data);

    academy GetByIdAcademy(Integer id);


    PageInfo<specialty> GetSpecialtyList(Integer page, Integer pageSize, Integer acId, String select);

    Integer addSpecialty(specialty data);

    Integer editSpecialty(specialty data);

    Integer DeleteSpecialty(Integer id);

    PageInfo<dean> GetAdminUser(Integer page, Integer pageSize);

    Integer AddAdminUser(dean data);

    Integer EditAdminUser(dean data);

    dean GetByIdAdmin(Integer id);

    Integer DeleteAdminUser(Integer id);
}
