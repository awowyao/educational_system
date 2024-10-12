package com.cwy.educationaladministrationsystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TokenApi {
    static String HMAC256key = "jiaowuGuanLiXiTong";
    public static String getToken(String UserName, String UserPws, Integer id,Integer userType){
        Map<String, Object> map = new HashMap<>();
//        Map<String, String> returnMap = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        // 过期时间
        instance.add(Calendar.DATE,5);
        String token = JWT.create()
                .withHeader(map)
                .withClaim("UserName", UserName)
                .withClaim("UserPws", UserPws)
                .withClaim("uid", id)
                .withClaim("userType", userType)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(HMAC256key));
//        returnMap.put("token", token);
//        returnMap.put("name", UserName);
        return token;
    }

    public static Map<String,String> parseToken(String Token){

        Map<String, String> returnMap = new HashMap<>();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(HMAC256key)).build();
        DecodedJWT verify = jwtVerifier.verify(Token);
        returnMap.put("userName", verify.getClaim("UserName").asString());
        returnMap.put("UserPws", verify.getClaim("UserPws").asString());
        returnMap.put("uid", verify.getClaim("uid").toString());
        returnMap.put("userType", String.valueOf(verify.getClaim("userType")));
        return returnMap;
    }
}
