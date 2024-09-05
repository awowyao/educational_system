package com.cwy.educationaladministrationsystem.model;



import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Data
public class pathList {
    private List<String> teacherPath = Stream.of(
            "/teacherApi/GetByTeacherStudent",
                    "/teacherApi/GetStudentComplaint",
                    "/teacherApi/GetByExamStudent",
                    "/teacherApi/GetByTeacherClass",
                    "/teacherApi/GetTeacherCurriculum",
                    "/loginApi/updatePassword",
                    "/teacherApi/DeleteStudentComplaint",
                    "/teacherApi/editStudentScores",
                    "/teacherApi/GetClassAndCourse",
                    "/loginApi/upload",
                    "/teacherApi/GetTeacherUserData",
                    "/teacherApi/editTeacherUserData",
                    "/studentApi/GetStudentCurriculum")
            .collect(Collectors.toList());
    private List<String> studentPath = Stream.of(
                    "/loginApi/updatePassword",
                    "/studentApi/GetStudentCurriculum",
                    "/studentApi/GetComplaintTable",
                    "/studentApi/GetStudentScores",
                    "/studentApi/getElectiveCourse",
                    "/studentApi/GetChooseCourseList",
                    "/studentApi/ComplaintCourse",
                    "/studentApi/chooseCourse",
                    "/studentApi/cancelCourse",
                    "/deanApi/selectAcademyOrClass",
                    "/studentApi/selectByIdStudent",
                    "/loginApi/upload",
                    "/studentApi/editStudentUser")
            .collect(Collectors.toList());

    private List<String> deanPath = Stream.of(
            "/loginApi/upload",
                    "/loginApi/updatePassword",
                    "/deanApi/selectStudent",
                    "/deanApi/selectAcademyOrClass",
                    "/deanApi/selectClass",
                    "/deanApi/addStudent",
                    "/deanApi/selectTeacher",
                    "/deanApi/deleteTeacher",
                    "/deanApi/resetTeacherPassword",
                    "/deanApi/selectByIdTeacher",
                    "/deanApi/editTeacher",
                    "/deanApi/addTeacher",
                    "/deanApi/selectCourse",
                    "/examApi/selectExamList",
                    "/deanApi/selectAcademyOrSpecialty",
                    "/examApi/deleteExam",
                    "/examApi/selectByIdExam",
                    "/examApi/editExam",
                    "/examApi/addExam",
                    "/classApi/getSpecialty",
                    "/examApi/BySpecialtyGetCourse",
                    "/deanApi/getClass",
                    "/classApi/addClass",
                    "/classApi/deleteClass",
                    "/classApi/selectByIdClass",
                    "/classApi/addClassCourse",
                    "/classApi/editClass",
                    "/deanApi/selectCourseData",
                    "/deanApi/deleteCourse",
                    "/deanApi/addCourse",
                    "/courseApi/GetElectiveCourse",
                    "/courseApi/GetElectiveCourse",
                    "/courseApi/addElectiveCourse",
                    "/courseApi/deleteElectiveCourse",
                    "/courseApi/GetElectiveCourseData",
                    "/courseApi/editElectiveCourse",
                    "/deanApi/deleteStudent",
                    "/deanApi/addTecherByCourse",
                    "/deanApi/editCourse",
                    "/classApi/ByCourseIdGetTeacher",
                    "/classApi/GetTeacherCurriculum",
                    "/deanApi/selectByIdStudent",
                    "/deanApi/editStudent",
                    "/classApi/GetCurriculum",
                    "/classApi/GetClassCourse",
                    "/classApi/editCurriculum",
                    "/classApi/ByAcademyIdGetCourse",
                    "/classApi/editTeacherCurriculum",
                    "/deanApi/selectByTeacherGetClass",
                    "/deanApi/selectByClassIdTeacherIdGetCourse",
                    "/deanApi/getDeanUserData",
                    "/deanApi/editDeanUserData",
            "/classApi/DeleteClassCourse")
            .collect(Collectors.toList());

}
