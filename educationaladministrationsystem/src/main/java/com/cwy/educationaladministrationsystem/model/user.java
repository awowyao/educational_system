package com.cwy.educationaladministrationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class user {
    private Integer id;
    private String name;
    @JsonIgnore
    private String password;
    private Integer userType;
    private String token;
}
