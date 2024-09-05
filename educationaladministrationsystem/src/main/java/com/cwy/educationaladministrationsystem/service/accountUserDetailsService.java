package com.cwy.educationaladministrationsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwy.educationaladministrationsystem.mapper.userMapper;
import com.cwy.educationaladministrationsystem.model.AccountUser;
import com.cwy.educationaladministrationsystem.model.UserS;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
@Service
public class accountUserDetailsService implements UserDetailsService {
    @Autowired
    private userMapper userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserS> userWrapper = new LambdaQueryWrapper<>();
        System.out.println(username);
        userWrapper.eq(UserS::getUsername,username);
        UserS user = userService.selectOne(userWrapper);
        System.out.println(user);
        return new User(username,user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
