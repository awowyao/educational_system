package com.cwy.educationaladministrationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cwy.educationaladministrationsystem.model.academy;
import com.cwy.educationaladministrationsystem.model.dean;
import com.cwy.educationaladministrationsystem.model.deanAuthority;
import com.cwy.educationaladministrationsystem.model.specialty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface adminMapper extends BaseMapper<Map> {
    deanAuthority selectByIdGetAuthority(@Param("deanId") Integer deanId,@Param("academyId") Integer academyId);

    void editDeanOperateLog(@Param("deanId") Integer deanId, @Param("academyId") Integer acId, @Param("log") String log);

    List<dean> selectDean(@Param("academyId") Integer acId);

    Integer AddDeanUser(dean data);

    void AddDeanAuthority(@Param("deanId") Integer deanId, @Param("academyId")  Integer acId,@Param("authority")  String authority);

    dean GetDeanData(@Param("id") Integer deanId);

    List<deanAuthority> selectDeanAuthority(@Param("deanId") Integer deanId);

    void deleteDeanA(@Param("id") Integer id);

    Integer EditDeanUser(dean data);

    Integer DeleteDeanUser(@Param("id") Integer id);

    List<academy> GetDeanAcademyList(@Param("id") Integer deanId);

    List<academy> GetAcademyList(@Param("select") String select);

    void addAcademy(academy data);

    Integer DeleteAcademy(@Param("id") Integer id);

    Integer editAcademy(academy data);

    academy GetByIdAcademy(@Param("id") Integer id);

    List<specialty> GetSpecialtyList(@Param("acId") Integer acId,@Param("select")  String select);

    void addSpecialty(specialty data);

    Integer editSpecialty(specialty data);

    Integer DeleteSpecialty(Integer id);

    List<dean> GetAdminUser();

    Integer AddAdminUser(dean data);

    Integer EditAdminUser(dean data);

    dean GetByIdAdmin(Integer id);

    Integer DeleteAdminUser(Integer id);
}
