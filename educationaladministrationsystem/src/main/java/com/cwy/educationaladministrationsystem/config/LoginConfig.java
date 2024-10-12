package com.cwy.educationaladministrationsystem.config;

import com.cwy.educationaladministrationsystem.interceptor.LoginInterceptor;
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


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file:C:\\Users\\yao\\Desktop\\华商毕业设计\\educationaladministrationsystem\\src\\main\\resources\\static\\";
        registry.addResourceHandler("/static/cover/**")    // 虚拟路径
                // file: 表示以本地的路径方式去访问绝对路径。
                .addResourceLocations(path + "userData\\");

    }

}


