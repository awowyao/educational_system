package com.cwy.educationaladministrationsystem.service.imp;

import com.alibaba.fastjson.JSON;
import com.cwy.educationaladministrationsystem.mapper.*;
import com.cwy.educationaladministrationsystem.model.*;
import com.cwy.educationaladministrationsystem.service.classService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

@Service
public class classServiceImp implements classService {

    @Autowired
    private deanMapper deanmapper;
    @Autowired
    private classMapper classmapper;
    @Autowired
    private courseMapper coursemapper;
    @Autowired
    private studentMapper studentmapper;

    @Autowired
    private teacherMapper teachermapper;
    @Override
    public List<Map> selectAcademyOrSpecialty(String academy, Integer deanId) {
        List<Map> returnList = new ArrayList<>();
        List<academy> data = deanmapper.selectAcademy(academy, deanId);
        for (int i=0;i<data.size();i++){
            Map mapData = new HashMap<>();
            mapData.put("value", data.get(i).getId());
            mapData.put("label", data.get(i).getName());

            List<specialty> specialtyList = classmapper.selectByAcademyIdFindspecialt(data.get(i).getId());
            List<Map> RClassList = new ArrayList<>();
            mapData.put("children",RClassList);
            for (int j=0;j<specialtyList.size();j++){
                Map<String, String> mapDataT = new HashMap<>();
                mapDataT.put("value", specialtyList.get(j).getName());
                mapDataT.put("label", specialtyList.get(j).getName());
                RClassList.add(mapDataT);
            }
            returnList.add(mapData);
        }
        return returnList;
    }

    @Override
    public List<specialty> selectSpecialty(Integer academyId) {
        return classmapper.selectByAcademyIdFindspecialt(academyId);
    }

    @Override
    public Integer deleteClass(Integer id) {
        classmapper.deleteClass(id);
        return 1;
    }

    @Override
    public List<ClassT> selectByIdClass(Integer id) {
        return classmapper.selectByIdClass(id);
    }

    @Override
    public Integer EditClass(ClassT data) {
        Date date = new Date();
        data.setUpdateTime(date);
        classmapper.EditClass(data);
        return 1;
    }

    @Override
    public List<curriculum> GetCurriculum(Integer id, Integer cid, Integer mode) {
        Date date = new Date();
        String curriculumData = "[{\"ClassTime\":\"(1,2)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]";

        if (cid==null || cid == 0){
            curriculum cdata = new curriculum();
            cdata.setUpdateTime(date);
            cdata.setCreateTime(date);
            cdata.setCurriculumData(curriculumData);
            if (mode == 3) {
                List<ClassT> clDataList = classmapper.selectByIdClass(id);
                ClassT clData = clDataList.get(0);
                if (clData.getCurriculumId() == null){
                    Integer ncid = classmapper.addCurriculum(cdata);
                    clData.setCurriculumId(cdata.getId());
                    classmapper.EditClass(clData);
                    cid = cdata.getId();
                } else {
                    cid = clData.getCurriculumId();
                }
            } else if (mode == 2) {
                teacher teacherData = deanmapper.selectByIdTeacher(id);
                if (teacherData == null) {
                    return null;
                }
                if (teacherData.getCurriculumId() == null){
                    Integer ncid = classmapper.addCurriculum(cdata);
                    teacherData.setCurriculumId(cdata.getId());
                    deanmapper.editTeacher(teacherData);
                    cid = cdata.getId();
                } else {
                    cid = teacherData.getCurriculumId();
                }
            } else if (mode == 1) {
                student studentData = deanmapper.selectByIdStudent(id);
                if (studentData.getCurriculumId() == null){
                    Integer ncid = classmapper.addCurriculum(cdata);
                    studentData.setCurriculumId(cdata.getId());
                    deanmapper.editStudent(studentData);
                    cid = cdata.getId();
                } else {
                    cid = studentData.getCurriculumId();
                }
            }
        }
        List<curriculum> serviceData = classmapper.GetCurriculum(cid);

        return serviceData;
    }

    @Override
    public List<course> ByAcademyIdGetCourse(Integer academyId) {
        return classmapper.ByAcademyIdGetCourse(academyId);
    }

    @Override
    public List<teacher> ByCourseIdGetTeacher(Integer id) {
        return classmapper.ByCourseIdGetTeacher(id);
    }

    public Integer EditTSCurriculum(List<curriculum> teacherCurriculum, List<Map> teacherCurriculumData, Map CurData,Integer mode, String courseType) {
        Date date = new Date();
        for (int i=0;i<teacherCurriculumData.size(); i++) {
            if (teacherCurriculumData.get(i).get("ClassTime").toString().equals(CurData.get("ClassTime"))){
                Map<String, String> newClassMap = new HashMap<>();
                if (mode == 1 || CurData.get("id") == null){
                    newClassMap.put("id", "");
                    newClassMap.put("class", "");
                    newClassMap.put("teacher", "");
                    newClassMap.put("teacherId", "");
                    newClassMap.put("className", "");
                    newClassMap.put("classId", "");
                    newClassMap.put("courseType", "");
                } else if (mode == 2) {
                    newClassMap.put("id", CurData.get("id").toString());
                    newClassMap.put("class", CurData.get("class").toString());
                    newClassMap.put("teacher", CurData.get("teacher").toString());
                    newClassMap.put("teacherId", CurData.get("teacherId").toString());
                    newClassMap.put("className", CurData.get("className").toString());
                    newClassMap.put("classId", CurData.get("classId").toString());
                    newClassMap.put("courseType", "必修");
                }else if (mode == 3) {
                    newClassMap.put("id", CurData.get("id").toString());
                    newClassMap.put("class", CurData.get("class").toString());
                    newClassMap.put("teacher", CurData.get("teacher").toString());
                    newClassMap.put("teacherId", CurData.get("teacherId").toString());
                    newClassMap.put("className", CurData.get("className").toString());
                    newClassMap.put("classId", CurData.get("classId").toString());
                    newClassMap.put("courseType", "选修");
                    Map<String, String> checkCurr = (Map<String, String>) teacherCurriculumData.get(i).get(CurData.get("Week"));
                    if (checkCurr.get("id") != null && !checkCurr.get("id").equals("")){
                        return 0;
                    }
                }
                teacherCurriculumData.get(i).replace(CurData.get("Week"),newClassMap);
                curriculum serviceTeacherData = new curriculum();
                serviceTeacherData.setCurriculumData(JSON.toJSONString(teacherCurriculumData));
                serviceTeacherData.setId(teacherCurriculum.get(0).getId());
                serviceTeacherData.setUpdateTime(date);
                classmapper.editCurriculum(serviceTeacherData);
                break;
            }
        }
        return 1;
    }

    @Override
    public Integer editCurriculum(Integer id, List<Map> curriculumData, Map oldCurData, Map newCurData, Integer cid) {
        curriculum serviceData = new curriculum();
        Date date = new Date();
        List<curriculum> teacherCurriculum = new ArrayList<>();
        List<Map> teacherCurriculumData = new ArrayList<>();
        List<curriculum> studentCurriculum = new ArrayList<>();
        List<Map> studentCurriculumData = new ArrayList<>();
        if (oldCurData.get("class") != null && !oldCurData.get("class").toString().equals("")){
            teacherCurriculum = GetCurriculum(Integer.valueOf(oldCurData.get("teacherId").toString()), 0,2);
            teacherCurriculumData = JSONObject.parseArray(teacherCurriculum.get(0).getCurriculumData(), Map.class);
            EditTSCurriculum(teacherCurriculum, teacherCurriculumData, oldCurData,1, "必修");

        }
        if (newCurData.get("class") != null && !newCurData.get("class").toString().equals("")) {
            teacherCurriculum = GetCurriculum(Integer.valueOf(newCurData.get("teacherId").toString()), 0,2);
            teacherCurriculumData = JSONObject.parseArray(teacherCurriculum.get(0).getCurriculumData(), Map.class);
            EditTSCurriculum(teacherCurriculum, teacherCurriculumData, newCurData,2, "必修");

        }
        List<student> studentList = deanmapper.selectStudent(0, cid, "0",0);
        for(int i=0;i<studentList.size();i++) {
            studentCurriculum = GetCurriculum(studentList.get(i).getId(), 0,1);

            studentCurriculumData = JSONObject.parseArray(studentCurriculum.get(0).getCurriculumData(), Map.class);
            EditTSCurriculum(studentCurriculum, studentCurriculumData,newCurData ,2, "必修");
        }
        String curriculumDataToString = JSON.toJSONString(curriculumData);
        serviceData.setId(id);
        serviceData.setUpdateTime(date);
        serviceData.setCurriculumData(curriculumDataToString);
        return classmapper.editCurriculum(serviceData);
    }

    @Override
    // cid 课程表id
    public Integer editTeacherCurriculum(Integer id, List<Map> curriculumData, Map oldCurData, Map newCurData, Integer cid) {
        if (oldCurData.get("class") == null || oldCurData.get("class").toString().equals("")){
            List<ClassT> classData = classmapper.selectByIdClass(Integer.valueOf(newCurData.get("classId").toString()));
            editCurriculum(classData.get(0).getCurriculumId(), curriculumData, oldCurData, newCurData, Integer.valueOf(newCurData.get("classId").toString()));
        } else if (newCurData.get("class") == null || newCurData.get("class").toString().equals("")) {

            List<ClassT> classData = classmapper.selectByIdClass(Integer.valueOf(oldCurData.get("classId").toString()));

            editCurriculum(classData.get(0).getCurriculumId(), curriculumData, oldCurData, newCurData, Integer.valueOf(oldCurData.get("classId").toString()));
        } else if (Integer.valueOf(newCurData.get("classId").toString()) == Integer.valueOf(oldCurData.get("classId").toString())) {
            List<ClassT> classData = classmapper.selectByIdClass(Integer.valueOf(oldCurData.get("classId").toString()));

            editCurriculum(classData.get(0).getCurriculumId(), curriculumData, oldCurData, newCurData, Integer.valueOf(oldCurData.get("classId").toString()));

        } else if (Integer.valueOf(newCurData.get("classId").toString()) != Integer.valueOf(oldCurData.get("classId").toString())){

            Map newMap = new HashMap<>();
            newMap.put("id", "");
            newMap.put("class", "");
            newMap.put("teacher", "");
            newMap.put("teacherId", "");
            newMap.put("className", "");
            newMap.put("classId", "");
            List<ClassT> classData = classmapper.selectByIdClass(Integer.valueOf(oldCurData.get("classId").toString()));
            List<ClassT> classData2 = classmapper.selectByIdClass(Integer.valueOf(newCurData.get("classId").toString()));
            editCurriculum(classData.get(0).getCurriculumId(), curriculumData, oldCurData, newMap, Integer.valueOf(oldCurData.get("classId").toString()));
            editCurriculum(classData2.get(0).getCurriculumId(), curriculumData, newMap, newCurData, Integer.valueOf(newCurData.get("classId").toString()));
        }
        return 1;
    }

    public Integer deleteCurriculum(Integer cid, Integer classId) {
        curriculum curriculumData =  classmapper.GetCurriculum(cid).get(0);
        List<Map> curriculumDataMap = JSONObject.parseArray(curriculumData.getCurriculumData(), Map.class);
        for (int i=0; i< curriculumDataMap.size(); i++){
            for (Object entry : curriculumDataMap.get(i).entrySet()) {
                Map.Entry<String, Object> Entry = (Map.Entry<String, Object>) entry;
                if (((String)Entry.getKey()).equals("ClassTime")){
                    continue;
                }
                Map<String, Object> DataMap = (Map<String, Object>) Entry.getValue();
                if (DataMap.get("id") != null && !(DataMap.get("id").toString().equals(""))){
                    Integer Id = Integer.valueOf(DataMap.get("id").toString());
                    if (Id == classId) {
                        Map newMap = new HashMap<>();
                        newMap.put("id", "");
                        newMap.put("class", "");
                        newMap.put("teacher", "");
                        newMap.put("teacherId", "");
                        newMap.put("className", "");
                        newMap.put("classId", "");
                        curriculumDataMap.get(i).replace((String)Entry.getKey(), newMap);
                    }
                }

            }
        }

        String curriculumDataToString = JSON.toJSONString(curriculumDataMap);
        Date date = new Date();
        curriculum serviceData = new curriculum();
        serviceData.setId(curriculumData.getId());
        serviceData.setUpdateTime(date);
        serviceData.setCurriculumData(curriculumDataToString);
        return classmapper.editCurriculum(serviceData);
    }

    @Override
    public Integer addClassCourse(Integer classId, Integer courseId, Integer teacherId) {
        classmapper.addClassCourse(classId, courseId, teacherId);
        List<student> studentList = deanmapper.selectStudent(0,classId,"0",0);
        for (int i=0; i<studentList.size(); i++){
            studentmapper.addStudentCourse(studentList.get(i).getId(), courseId, teacherId);
        }
        return null;
    }

    @Override
    public List<course> GetClassCourse(Integer classId) {
        return classmapper.GetClassCourse(classId);
    }

    @Override
    public Integer DeleteClassCourse(Integer classId, Integer courseId) {
        List<student> studentList = deanmapper.selectStudent(0,classId,"0",0);
        for (int i=0;i<studentList.size(); i++){
            if (studentList.get(i).getCurriculumId() != null){
                deleteCurriculum(studentList.get(i).getCurriculumId(), courseId);
            }
            studentmapper.deleteStudentCourse(studentList.get(i).getId(), courseId);
        }
        Integer teacherId = teachermapper.ByCourseIdClassIdGetTeacher(classId, courseId);
        teacher teacherData = deanmapper.selectByIdTeacher(teacherId);
        ClassT classData = classmapper.selectByIdClass(classId).get(0);
        deleteCurriculum(classData.getCurriculumId(), courseId);
        deleteCurriculum(teacherData.getCurriculumId(), courseId);
        classmapper.DeleteClassCourse(classId, courseId);
        return 1;
    }

    @Override
    public List<ClassT> GetByTeacherClass(Integer teacherId) {

        List<ClassT> classList = classmapper.GetByTeacherClass(teacherId);

        return classList;
    }

    @Override
    public List<ClassT> selectByTeacherGetClass(Integer id) {
        return classmapper.selectByTeacherGetClass(id);
    }

    @Override
    public List<Map<String, Object>> GetClassAndCourse(Integer teacherId) {
        List<ClassT> classList = classmapper.selectByTeacherGetClass(teacherId);
        List<Map<String, Object>> RList = new ArrayList<>();
        for (int i=0;i<classList.size();i++) {
            Map<String, Object> classMap = new HashMap<>();
            classMap.put("label", classList.get(i).getName());
            classMap.put("value", classList.get(i).getId());
            List<Map<String, Object>> courseListR = new ArrayList<>();
            List<course> courseList = coursemapper.selectByClassIdTeacherIdGetCourse(classList.get(i).getId(), teacherId);
            System.out.println(courseList);
            for (int j=0;j<courseList.size();j++) {
                Map<String, Object> courseMap = new HashMap<>();
                courseMap.put("label", courseList.get(j).getName());
                courseMap.put("value", courseList.get(j).getId());
                courseListR.add(courseMap);
            }
            classMap.put("children", courseListR);
            RList.add(classMap);
        }
        return RList;
    }


    @Override
    public PageInfo<ClassT> selectClass(Integer page, Integer pageSize, String specialtyName, Integer academyId, String select,Integer deanId) {
        PageHelper.startPage(page,pageSize);
        List<ClassT> data = classmapper.selectClass(specialtyName, academyId, select, deanId);
        PageInfo<ClassT> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }
}
