package com.cwy.educationaladministrationsystem.service.imp;

import com.cwy.educationaladministrationsystem.mapper.dean.examMapper;
import com.cwy.educationaladministrationsystem.mapper.studentMapper;
import com.cwy.educationaladministrationsystem.model.course;
import com.cwy.educationaladministrationsystem.model.student;
import com.cwy.educationaladministrationsystem.service.studentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class studentServiceImp implements studentService {

    @Autowired
    private studentMapper studentmapper;

    @Autowired
    private examMapper exammapper;

    @Override
    public PageInfo<student> GetByTeacherIdStudent(Integer page, Integer pageSize, Integer teacherId, Integer classId, String select) {

        PageHelper.startPage(page,pageSize);
        List<student> data = studentmapper.GetByTeacherIdStudent(teacherId, classId, select);
        PageInfo<student> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    public PageInfo<student> GetByExamStudent(Integer page, Integer pageSize, Integer teacherId, Integer classId, String select, Integer courseId) {
        List<student> data = studentmapper.GetByExamStudent(teacherId, classId, select, courseId);
        List<student> eleData = studentmapper.GetByEleExamStudent(teacherId, classId, select, courseId);
        List<student> res = Stream.of(data, eleData).flatMap(Collection::stream).collect(Collectors.toList());
        com.github.pagehelper.Page pageI = new com.github.pagehelper.Page(page, pageSize);
        //为Page类中的total属性赋值
        pageI.setTotal(res.size());
        //计算当前需要显示的数据下标起始值
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, res.size());
        //从链表中截取需要显示的子链表，并加入到Page
        pageI.addAll(res.subList(startIndex,endIndex));
        PageInfo<student> pageInfo = new PageInfo<>(res);
        return pageInfo;

    }

    @Override
    public Integer editStudentScores(Integer studentExamId, Integer scores, Integer def) {
        if (def!=null && def == 2) {
            exammapper.ComplainteditStudentScores(studentExamId);
        }
        return exammapper.editStudentScores(studentExamId, scores);
    }

    @Override
    public PageInfo<student> GetStudentComplaint(Integer page, Integer pageSize, Integer teacherId, Integer classId, String select, Integer courseId) {
        List<student> data = studentmapper.GetStudentComplaint(teacherId, classId, select);
        List<student> eleData = studentmapper.GetStudentEleComplaint(teacherId, classId, select, courseId);
        List<student> res = Stream.of(data, eleData).flatMap(Collection::stream).collect(Collectors.toList());
        com.github.pagehelper.Page pageI = new com.github.pagehelper.Page(page, pageSize);
        //为Page类中的total属性赋值
        pageI.setTotal(res.size());
        //计算当前需要显示的数据下标起始值
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, res.size());
        //从链表中截取需要显示的子链表，并加入到Page
        pageI.addAll(res.subList(startIndex,endIndex));
        PageInfo<student> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }

    @Override
    public Integer DeleteStudentComplaint(Integer studentExamId) {
        return studentmapper.DeleteStudentComplaint(studentExamId);
    }

    @Override
    public PageInfo<course> GetStudentScores(Integer page, Integer pageSize, Integer studentId, String select, String year) {
        PageHelper.startPage(page,pageSize);
        List<course> data = studentmapper.GetStudentScores(studentId, select, year);
        PageInfo<course> pageInfo = new PageInfo<>(data);

        return pageInfo;
    }

    @Override
    public Integer ComplaintCourse(Integer courseId, Integer teacherId, Integer seId) {
        Date date = new Date();
        try {
            studentmapper.ComplaintCourse(courseId, teacherId, seId, date);
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
    public PageInfo<Map> GetComplaintTable(Integer studentId, Integer page, Integer pageSize, String select) {
        List<Map> data = studentmapper.GetComplaintTable(studentId, select);
        List<Map> eleData = studentmapper.GetEleComplaintTable(studentId, select);
        List<Map> res = Stream.of(data, eleData).flatMap(Collection::stream).collect(Collectors.toList());
        com.github.pagehelper.Page pageI = new com.github.pagehelper.Page(page, pageSize);
        //为Page类中的total属性赋值
        pageI.setTotal(res.size());
        //计算当前需要显示的数据下标起始值
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, res.size());
        //从链表中截取需要显示的子链表，并加入到Page
        pageI.addAll(res.subList(startIndex,endIndex));
        PageInfo<Map> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }


}
