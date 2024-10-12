package com.cwy.educationaladministrationsystem.service.imp;

import com.cwy.educationaladministrationsystem.mapper.adminMapper;
import com.cwy.educationaladministrationsystem.mapper.classMapper;
import com.cwy.educationaladministrationsystem.mapper.dean.examMapper;
import com.cwy.educationaladministrationsystem.mapper.deanMapper;
import com.cwy.educationaladministrationsystem.mapper.studentMapper;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.classService;
import com.cwy.educationaladministrationsystem.service.deanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class deanServiceImp implements deanService {

    @Autowired
    private deanMapper deanmapper;

    @Autowired
    private classService classservice;
    @Autowired
    private studentMapper studentmapper;
    @Autowired
    private classMapper classmapper;
    @Autowired
    private examMapper exammapper;

    @Autowired
    private adminMapper adminmapper;
    private String Salt = "jiaowuguan";

    @Override
    public Integer addCourse(course data) {
        Date date = new Date();
        data.setCreateTime(date);
        data.setUpdateTime(date);

        Integer courseNub = deanmapper.SelectCourseNub(data.getName(), data.getAcademyId());
        if (courseNub == 0) {
            try {
                deanmapper.addCourse(data);
            } catch (Exception e){
                if(e instanceof DuplicateKeyException){
                    return 0;
                }else {
                    return -1;
                }
            }
            return 1;
        }else {
            return 0;
        }

    }



    @Override
    public Integer addTeacher(teacher data) {
        Date date = new Date();
        SimpleDateFormat teacherIdTime = new SimpleDateFormat("yy");
        List<teacher> teacherList = deanmapper.selectTeacher(data.getAcademyId(), "0", 0);
        String teacherId = teacherIdTime.format(date) + data.getAcademyId() + teacherList.size();
        String Password = DigestUtils.md5DigestAsHex("123456".getBytes());
        Password = teacherId + Salt + Password;
        Password = DigestUtils.md5DigestAsHex(Password.getBytes());
        data.setPassword(Password);
        data.setId(Integer.valueOf(teacherId));
        data.setCreateTime(date);
        data.setUpdateTime(date);
        try {
            deanmapper.addTeacher(data);
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
    public Integer addStudent(student data) {
        Date date = new Date();
        SimpleDateFormat teacherIdTime = new SimpleDateFormat("yy");
        Integer classId = data.getClassId();
        if (classId == null) {
            classId = 0;
        }
        List<student> studentList = deanmapper.selectStudent(data.getAcademyId(),classId,"0",0);

        String teacherId = teacherIdTime.format(date) +
                data.getAcademyId().toString().substring(data.getAcademyId().toString().length()-3) + classId +
                studentList.size();

        String Password = DigestUtils.md5DigestAsHex("123456".getBytes());
        Password = teacherId + Salt + Password;
        Password = DigestUtils.md5DigestAsHex(Password.getBytes());
        data.setPassword(Password);
        data.setId(Integer.valueOf(teacherId));
        data.setCreateTime(date);
        data.setUpdateTime(date);


        try {
            deanmapper.addStudent(data);
            if (data.getClassId()!=null && data.getClassId()!=0){
                List<course> newCourseList = classservice.GetClassCourse(data.getClassId());
                for (int i=0;i<newCourseList.size();i++){
                    studentmapper.addStudentCourse(data.getId(), newCourseList.get(i).getId(),newCourseList.get(i).getTeacherId());
                    exam examData = exammapper.selectByCourseIdGetExam(newCourseList.get(i).getId());
                    if (examData != null) {
                        exammapper.addStudentExam(data.getId(),examData.getId());
                    }
                }
                curriculum Curriculum = classservice.GetCurriculum(Integer.valueOf(teacherId), 0,1).get(0);
                curriculum ClassCurriculum = classservice.GetCurriculum(data.getClassId(), 0,3).get(0);
                Curriculum.setCurriculumData(ClassCurriculum.getCurriculumData());
                classmapper.editCurriculum(Curriculum);
            }
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
    public Integer addClass(ClassT data) {
        Date date = new Date();
        data.setCreateTime(date);
        data.setUpdateTime(date);
        data.setNumber(0);
        deanmapper.addClass(data);
        return 1;
    }

    @Override
    public Integer StudentSetClass(Integer id, Integer classId) {
        Date date = new Date();
        try {
            deanmapper.StudentSetClass(id, classId, date);
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
    public Integer StudentSetAcademy(Integer id, Integer academyId) {
        Date date = new Date();
        try {
            deanmapper.StudentSetAcademy(id, academyId, date);
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
    public Integer editTeacher(teacher data) {
        Date date = new Date();
        data.setUpdateTime(date);
        deanmapper.editTeacher(data);
        return 1;
    }

    @Override
    public Integer editStudent(student data) {
        Date date = new Date();
        data.setUpdateTime(date);
        try {
            student studentData = deanmapper.selectByIdStudent(data.getId());
            deanmapper.editStudent(data);
            if (studentData.getClassId() != data.getClassId()){
                List<course> oldCourseList = classservice.GetClassCourse(studentData.getClassId());
                for (int i=0;i<oldCourseList.size();i++){
                    studentmapper.deleteStudentCourse(data.getId(), oldCourseList.get(i).getId());
                }
                if (data.getClassId()!=0 && data.getClassId()!=null){
                    List<course> newCourseList = classservice.GetClassCourse(data.getClassId());
                    for (int i=0;i<newCourseList.size();i++){
                        try {
                            studentmapper.addStudentCourse(data.getId(), newCourseList.get(i).getId(), newCourseList.get(i).getTeacherId());
                        }catch (Exception e){
                            continue;
                        }
                    }
                    curriculum Curriculum = classservice.GetCurriculum(data.getId(), 0,1).get(0);
                    curriculum ClassCurriculum = classservice.GetCurriculum(data.getClassId(), 0,3).get(0);
                    Curriculum.setCurriculumData(ClassCurriculum.getCurriculumData());
                    classmapper.editCurriculum(Curriculum);
                }
            }

        } catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public PageInfo<student> selectStudent(Integer page, Integer pageSize, Integer acId, Integer classId, String SelectStudent, Integer deanId) {
        PageHelper.startPage(page,pageSize);
        List<student> data = deanmapper.selectStudent(acId, classId, SelectStudent, deanId);
        PageInfo<student> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    public PageInfo<teacher> selectTeacher(int page, int pageSize, Integer acId, String selectStudent, Integer deanId) {
        List<teacher> data = new ArrayList<>();
        if (pageSize <100){
            PageHelper.startPage(page,pageSize);
            data = deanmapper.selectTeacher(acId, selectStudent, deanId);
        }else {
            data = deanmapper.selectTeacher(acId, selectStudent, deanId);
        }
        PageInfo<teacher> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    public Integer deleteTeacher(Integer id) {
        deanmapper.deleteTeacher(id);
        return 1;
    }
    @Override
    public Integer deleteCourse(Integer id) {
        deanmapper.deleteCourse(id);
        return 1;
    }

    @Override
    public Integer addTeacherByCourse(Integer id, List<Integer> academyList) {

        if (academyList.size()>0){
            for(int i=0; i<academyList.size(); i++) {
                deanmapper.addTeacherByCourse(id, academyList.get(i));
            }
            return 1;
        }
        return 0;
    }

    @Override
    public course ByAcademyIdAndNameSelectCourse(String courseName, Integer academyId) {
        return deanmapper.ByAcademyIdAndNameSelectCourse(courseName, academyId);
    }

    @Override
    public course selectCourseData(Integer id) {
        course serviceData = deanmapper.selectCourseData(id);
        List<Integer> teacherList = new ArrayList<>();
        try {
            List<teacher> teachers = deanmapper.selectCourseTeacher(id);
            for (int i=0; i<teachers.size(); i++) {
                teacherList.add(teachers.get(i).getId());
            }
            serviceData.setTeacherList(teacherList);
        } catch (Exception e) {
            serviceData.setTeacherList(teacherList);
        }
        return serviceData;
    }

    @Override
    public Integer editCourse(course data) {
        deanmapper.editCourse(data);
        deanmapper.delectCourseTeacherById(data.getId());
        for(int i=0; i<data.getTeacherList().size(); i++) {
            deanmapper.addTeacherByCourse(data.getId(), data.getTeacherList().get(i));
        }
        return 1;
    }

    @Override
    public Integer editDeanUserData(dean data) {
        return adminmapper.EditDeanUser(data);
    }


    @Override
    public Integer deleteStudent(Integer id) {
        deanmapper.deleteStudent(id);
        return 1;
    }

    @Override
    public List<Map> selectAcademyOrClass(String academy, Integer deanId) {
        List<Map> returnList = new ArrayList<>();
        List<academy> data = deanmapper.selectAcademy(academy, deanId);
        for (int i=0;i<data.size();i++){
            Map mapData = new HashMap<>();
            mapData.put("value", data.get(i).getId());
            mapData.put("label", data.get(i).getName());

            List<ClassT> classList = deanmapper.selectByAcademyIdFindClass(data.get(i).getId());
            List<Map> RClassList = new ArrayList<>();
            mapData.put("children",RClassList);
            for (int j=0;j<classList.size();j++){
                Map<String, String> mapDataT = new HashMap<>();
                mapDataT.put("value", classList.get(j).getId().toString());
                mapDataT.put("label", classList.get(j).getName());
                RClassList.add(mapDataT);
            }
            returnList.add(mapData);
        }
        return returnList;
    }

    @Override
    public List<ClassT> selectClass(Integer academyId) {
        List<ClassT> classList = deanmapper.selectByAcademyIdFindClass(academyId);
        return classList;
    }

    @Override
    public student selectByIdStudent(Integer studentId) {
        return deanmapper.selectByIdStudent(studentId);
    }

    @Override
    public teacher selectByIdTeacher(Integer id) {
        return deanmapper.selectByIdTeacher(id);
    }

    @Override
    public PageInfo<course> selectCourse(Integer page, Integer pageSize, Integer acId, String selectData, Integer deanId) {
        PageHelper.startPage(page,pageSize);
        List<course> data = deanmapper.selectCourse(acId, selectData, deanId);
        PageInfo<course> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }
}
