package com.cwy.educationaladministrationsystem.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cwy.educationaladministrationsystem.intercept.StudentLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.adminLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.deanLoginIntercept;
import com.cwy.educationaladministrationsystem.intercept.teacherLoginIntercept;
import com.cwy.educationaladministrationsystem.mapper.loginMapper;
import com.cwy.educationaladministrationsystem.model.pathList;
import com.cwy.educationaladministrationsystem.model.result;
import com.cwy.educationaladministrationsystem.model.user;
import com.cwy.educationaladministrationsystem.util.ThreadLocalApi;
import com.cwy.educationaladministrationsystem.util.TokenApi;
import com.cwy.educationaladministrationsystem.util.checkPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.*;

@Component
@CrossOrigin
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private loginMapper loginmapper;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String Salt = "jiaowuguan";
        if (handler.getClass().getName().equals("org.springframework.web.servlet.resource.ResourceHttpRequestHandler")){
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        StudentLoginIntercept StudentLoginIntercept =
                handlerMethod.getMethod()
                        .getAnnotation(StudentLoginIntercept.class);

        teacherLoginIntercept teacherLoginIntercept = handlerMethod.getMethod()
                .getAnnotation(teacherLoginIntercept.class);

        deanLoginIntercept deanLoginIntercept = handlerMethod.getMethod()
                .getAnnotation(deanLoginIntercept.class);

        adminLoginIntercept adminLoginIntercept = handlerMethod.getMethod()
                .getAnnotation(adminLoginIntercept.class);

        if (StudentLoginIntercept == null && teacherLoginIntercept == null && deanLoginIntercept == null && adminLoginIntercept == null) {
            return true;
        }else {

            String token = request.getHeader("Token");
            if (token == null || token == ""){
                result baseResult = new result();
                baseResult.setCode(401);
                baseResult.setMsg("未登录");
                String jsonObjectStr = JSONObject.toJSONString(baseResult);
                returnJson(response,jsonObjectStr, 401);
                return false;
            }
            Map<String, String> prToken = TokenApi.parseToken(token);
            String userId = prToken.get("uid");
            String userName = prToken.get("userName");
            String Pws = prToken.get("UserPws");

            Integer userType = Integer.valueOf(prToken.get("userType"));
            user userLength = new user();
            if (userType == 1 && StudentLoginIntercept != null){
                userLength = loginmapper.studentLogin(userId, Pws);
            } else if (userType == 2 && teacherLoginIntercept != null) {
                userLength = loginmapper.teacherLogin(userId, Pws);
            } else if (userType == 3 && deanLoginIntercept !=  null) {
                userLength = loginmapper.deanLogin(userId, Pws);
            } else if (userType == 4) {
                userLength = loginmapper.adminLogin(userName ,Pws);
            }
            if (userLength != null){

                if (userType == 1 && StudentLoginIntercept != null && checkPath.studentPath(request.getServletPath())){
                    ThreadLocalApi.set(prToken);
                    return true;
                } else if (userType == 2 && teacherLoginIntercept != null && checkPath.teacherPath(request.getServletPath())) {
                    ThreadLocalApi.set(prToken);
                    return true;
                } else if (userType == 3 && deanLoginIntercept != null && checkPath.deanPath(request.getServletPath())) {
                    ThreadLocalApi.set(prToken);
                    return true;
                } else if (userType == 4) {
                    ThreadLocalApi.set(prToken);
                    return true;
                }
                System.out.println(request.getServletPath());
                result baseResult = new result();
                baseResult.setCode(401);
                baseResult.setMsg("无权限");
                String jsonObjectStr = JSONObject.toJSONString(baseResult);
                returnJson(response,jsonObjectStr, 401);
                return false;
            }else {
                result baseResult = new result();
                baseResult.setCode(401);
                baseResult.setMsg("令牌无效");
                String jsonObjectStr = JSONObject.toJSONString(baseResult);
                returnJson(response,jsonObjectStr, 401);
                return false;
            }
        }

    }
    private void returnJson(HttpServletResponse response, String json, Integer status) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(status);
        writer = response.getWriter();
        writer.print(json);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalApi.remove();
    }
}
