package com.cwy.educationaladministrationsystem.service.imp.dean;


import com.cwy.educationaladministrationsystem.mapper.classMapper;
import com.cwy.educationaladministrationsystem.mapper.courseMapper;
import com.cwy.educationaladministrationsystem.mapper.dean.examMapper;
import com.cwy.educationaladministrationsystem.mapper.studentMapper;
import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.exam;
import com.cwy.educationaladministrationsystem.model.student;

import com.cwy.educationaladministrationsystem.service.dean.examService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class examServiceImp implements examService {
    @Autowired
    private examMapper exammapper;
    @Autowired
    private studentMapper studentmapper;

    @Autowired
    private courseMapper coursemapper;
    @Override
    public PageInfo<exam> selectExamList(Integer page, Integer pageSize, Integer acId, String specialty_name, String selectData, Integer deanId) {
        PageHelper.startPage(page,pageSize);
        List<exam> data = exammapper.selectExamList(acId, specialty_name, selectData, deanId);
        PageInfo<exam> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    public List<course> BySpecialtyGetCourse(String specialty) {
        return exammapper.BySpecialtyGetCourse(specialty);
    }

    @Override
    public Integer addExam(exam data) {
        Date date = new Date();
        data.setUpdateTime(date);
        data.setCreateTime(date);
        if (data.getCourseId() !=null){
            List<student> studentList = studentmapper.selectBycourseStudent(data.getCourseId());
            exammapper.addExam(data);
            for (int i=0; i<studentList.size();i++){
                exammapper.addStudentExam(studentList.get(i).getId(),data.getId());
            }
        } else if (data.getElectveCourseId() !=null) {
            List<student> studentList = studentmapper.ByElectiveIdGetStudent(data.getElectveCourseId());
            exammapper.addExam(data);
            for (student i :studentList) {
                exammapper.addStudentExam(i.getId(),data.getId());
            }

        }

        return 1;
    }

    @Override
    public Integer deleteExam(Integer id) {
        exammapper.deleteStudentExam(id);
        return exammapper.deleteExam(id);
    }

    @Override
    public exam selectByIdExam(Integer id) {
        return exammapper.selectByIdExam(id);
    }

    @Override
    public Integer editExam(exam data) {
        Date date = new Date();
        data.setUpdateTime(date);
        return exammapper.editExam(data);
    }


}
