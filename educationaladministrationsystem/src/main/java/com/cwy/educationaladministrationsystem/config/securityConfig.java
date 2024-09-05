package com.cwy.educationaladministrationsystem.config;

import com.cwy.educationaladministrationsystem.service.accountUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class securityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin((formLogin) ->
            formLogin.loginProcessingUrl("/login")
                    .successHandler(new LoginSuccessHandler())
        );
        http.authorizeHttpRequests(authorizeHttpRequests->
                authorizeHttpRequests.requestMatchers("/login.html", "/user/login").permitAll()
                        .anyRequest().authenticated()
        );
        http.csrf((csrf)-> csrf.disable());
        return http.build();
    }

    public class LoginSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse
                response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("登录成功");
        }
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public AuthenticationProvider authProvider() {
//    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setUserDetailsService(new accountUserDetailsService());
//    return authProvider;
//    }
}
