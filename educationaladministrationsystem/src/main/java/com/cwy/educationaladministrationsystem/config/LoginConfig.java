package com.cwy.educationaladministrationsystem.config;

import com.cwy.educationaladministrationsystem.interceptor.LoginInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@EnableWebMvc
public class LoginConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor jwtInterceptor(){
        return new LoginInterceptor();
    }

//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
//        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
//        scannerConfigurer.setBasePackage("com.cwy.educationaladministrationsystem.*.mapper");
//        return scannerConfigurer;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file:E:\\毕业设计\\源代码和查重报告\\SpringBoot后端\\educationaladministrationsystem\\src\\main\\resources\\static\\";
        registry.addResourceHandler("/static/cover/**")    // 虚拟路径
                // file: 表示以本地的路径方式去访问绝对路径。
                .addResourceLocations(path + "userData\\");

    }

}


