package com.cwy.educationaladministrationsystem.util;


import com.cwy.educationaladministrationsystem.model.result;
import org.springframework.http.ResponseEntity;

public class ResultUtil {
    public static ResponseEntity<result> success(Integer code, String msg, Object object) {
        result result = new result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return ResponseEntity.status(200).body(result);
    }
    public static ResponseEntity<result> error(Integer code, String msg) {
        result result = new result();
        result.setCode(code);
        result.setMsg(msg);
        return ResponseEntity.status(code).body(result);
    }
}
