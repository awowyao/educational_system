package com.cwy.educationaladministrationsystem.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.mapper.courseMapper;
import com.cwy.educationaladministrationsystem.mapper.dean.examMapper;
import com.cwy.educationaladministrationsystem.mapper.deanMapper;
import com.cwy.educationaladministrationsystem.mapper.studentMapper;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.courseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class courseServiceImp implements courseService {
    @Autowired
    private courseMapper coursemapper;
    @Autowired
    private classServiceImp classserviceimp;
    @Autowired
    private deanMapper deanmapper;
    @Autowired
    private studentMapper studentmapper;
    @Autowired
    private examMapper exammapper;
    @deanLoginIntercept
    public Integer operationElectiveCurriculum(electiveCourse data, Integer mode) {
        Integer editCurrStatic = 0;
        teacher teacherData = deanmapper.selectByIdTeacher(data.getTeacherId());
        List<curriculum> Curriculum = new ArrayList<>();
        if (mode == 1 || mode == 2){
            Curriculum = classserviceimp.GetCurriculum(data.getTeacherId(), 0,2);
        }else if (mode ==3 || mode == 4){
            Curriculum = classserviceimp.GetCurriculum(data.getStudentId(), 0,1);
        }
        List<Map> CurriculumData = JSONObject.parseArray(Curriculum.get(0).getCurriculumData(), Map.class);
        List<String> courseTime = Arrays.asList(data.getClassTime().split("/"));
        Map newMap = new HashMap<>();
        newMap.put("ClassTime",courseTime.get(1));
        newMap.put("Week",courseTime.get(0));
        newMap.put("id", data.getId());
        newMap.put("class", data.getName());
        newMap.put("teacher", teacherData.getName());
        newMap.put("teacherId", data.getTeacherId());
        newMap.put("className", "");
        newMap.put("classId", "");
        if (mode == 1 || mode == 3){
            editCurrStatic = classserviceimp.EditTSCurriculum(Curriculum, CurriculumData, newMap,3, "选修");
        }else if (mode == 2 || mode == 4){
            editCurrStatic = classserviceimp.EditTSCurriculum(Curriculum, CurriculumData, newMap,1, "");
        }
        return editCurrStatic;
    }
    @deanLoginIntercept
    @Override
    public Integer addElectiveCourse(electiveCourse data) {
        Date date = new Date();
        data.setCreateTime(date);
        data.setUpdateTime(date);
        coursemapper.addElectiveCourse(data);
        Integer editCurrStatic = operationElectiveCurriculum(data,1);

        if (editCurrStatic==1){
            return 1;
        }else {
            deleteElectiveCourse(data.getId());
            return 0;
        }

    }

    @deanLoginIntercept
    @Override
    public Integer editElectiveCourse(electiveCourse data) {
        Date date = new Date();
        data.setUpdateTime(date);
        electiveCourse oldCourse = ByIdGetElectiveCourse(data.getId());
        operationElectiveCurriculum(oldCourse,2);
        List<student> studentList = studentmapper.ByElectiveIdGetStudent(data.getId());
        for (int i=0; i<studentList.size();i++) {
            oldCourse.setStudentId(studentList.get(i).getId());
            operationElectiveCurriculum(oldCourse,4);
        }
        Integer editCurrStatic = operationElectiveCurriculum(data,1);
        for (int i=0; i<studentList.size();i++) {
            data.setStudentId(studentList.get(i).getId());
            Integer StudentStatic = operationElectiveCurriculum(data,3);
            if (StudentStatic == 0){
                for (int j=0; j<i;j++) {
                    data.setStudentId(studentList.get(i).getId());
                    operationElectiveCurriculum(data,4);
                    oldCourse.setStudentId(studentList.get(j).getId());
                    operationElectiveCurriculum(oldCourse,3);
                }
                return 2;
            }
        }
        if (editCurrStatic==1){
            coursemapper.editElectiveCourse(data);
            return 1;
        }else {
            operationElectiveCurriculum(oldCourse,1);
            return 0;
        }
    }
    @deanLoginIntercept
    @Override
    public Integer deleteElectiveCourse(Integer id) {
        electiveCourse data = coursemapper.ByIdGetElectiveCourse(id);
        operationElectiveCurriculum(data,2);
        List<student> studentList = studentmapper.ByElectiveIdGetStudent(data.getId());
        for (int i=0; i<studentList.size();i++) {
            data.setStudentId(studentList.get(i).getId());
            operationElectiveCurriculum(data,4);
        }
        return coursemapper.deleteElectiveCourse(id);
    }

    @Override
    public Integer chooseCourse(Integer studentId, Integer cid) {
        electiveCourse data = coursemapper.ByIdGetElectiveCourse(cid);
        exam examData = exammapper.selectByElectiveCourseIdGetExam(cid);
        electiveCourse electiveCourseData = coursemapper.selectByIdElectiveCourse(cid);
        if (electiveCourseData.getTotal()<= electiveCourseData.getChooseNub()){
            return -1;
        }

        data.setStudentId(studentId);
        Integer chooseCourseState = coursemapper.chooseCourse(studentId, cid);
        if (chooseCourseState == 1){
            Integer Static = operationElectiveCurriculum(data,3);
            if (Static != 1) {
                coursemapper.CancelChooseCourse(cid, studentId);
                return 0;
            }
            if (examData != null){
                exammapper.addStudentExam(studentId, examData.getId());
            }
            return 1;
        }else {
            return -2;
        }
    }

    @Override
    public PageInfo<electiveCourse> StudentGetElectiveCourse(Integer page, Integer pageSize, String select, Integer academyId, Integer teacherId, Integer StudentId) {
        PageHelper.startPage(page, pageSize);
        List<electiveCourse> data = coursemapper.StudentGetElectiveCourse(academyId,teacherId,select, StudentId);
        PageInfo<electiveCourse> Rdata = new PageInfo<>(data);
        return Rdata;
    }

    @Override
    public PageInfo<electiveCourse> ByStudentIdGetElectiveCourse(Integer page, Integer pageSize, Integer studentId, Integer acId, Integer teacherId, String select) {
        PageHelper.startPage(page, pageSize);
        List<electiveCourse> data = coursemapper.ByStudentIdGetElectiveCourse(studentId);
        PageInfo<electiveCourse> Rdata = new PageInfo<>(data);
        return Rdata;
    }

    @Override
    public Integer CancelClass(Integer cid, Integer studentId) {
        electiveCourse data = coursemapper.ByIdGetElectiveCourse(cid);
        data.setStudentId(studentId);
        operationElectiveCurriculum(data,4);
        exam examData = exammapper.selectByElectiveCourseIdGetExam(cid);
        if (examData !=null){
            exammapper.deleteStudentIdExamId(studentId, examData.getId());
        }
        coursemapper.CancelChooseCourse(cid, studentId);
        return 1;
    }

    @Override
    public List<course> selectByClassIdTeacherIdGetCourse(Integer id, Integer teacherId) {
        return coursemapper.selectByClassIdTeacherIdGetCourse(id, teacherId);
    }


    @Override
    public PageInfo<electiveCourse> GetElectiveCourse(Integer page, Integer pageSize, Integer acId, Integer teacherId, String select,Integer deanId) {
        PageHelper.startPage(page, pageSize);
        List<electiveCourse> data = coursemapper.GetElectiveCourse(acId, teacherId, select, deanId);
        PageInfo<electiveCourse> pageData = new PageInfo<>(data);
        return pageData;
    }

    @Override
    public electiveCourse ByIdGetElectiveCourse(Integer id) {
        return coursemapper.ByIdGetElectiveCourse(id);
    }






}
