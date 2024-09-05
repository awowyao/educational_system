package com.cwy.educationaladministrationsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cwy.educationaladministrationsystem.mapper")
public class EducationaladministrationsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationaladministrationsystemApplication.class, args);
    }

}
