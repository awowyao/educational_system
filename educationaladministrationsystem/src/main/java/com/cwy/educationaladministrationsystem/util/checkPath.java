package com.cwy.educationaladministrationsystem.util;

import com.cwy.educationaladministrationsystem.model.pathList;
import com.cwy.educationaladministrationsystem.model.result;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class checkPath {
    static pathList pathL = new pathList();
    public static boolean teacherPath(String path) {
        List<String> ListD = pathL.getTeacherPath();
        Set<String> set = new HashSet<String>(ListD);
        return set.contains(path);
    }
    public static boolean studentPath(String path) {
        List<String> ListD = pathL.getStudentPath();
        Set<String> set = new HashSet<String>(ListD);
        return set.contains(path);
    }

    public static boolean deanPath(String path) {
        List<String> ListD = pathL.getDeanPath();
        Set<String> set = new HashSet<String>(ListD);
        return set.contains(path);
    }

}
