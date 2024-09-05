package com.cwy.educationaladministrationsystem.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwy.educationaladministrationsystem.mapper.userMapper;
import com.cwy.educationaladministrationsystem.model.AccountUser;
import com.cwy.educationaladministrationsystem.model.UserS;
import com.cwy.educationaladministrationsystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp extends ServiceImpl<userMapper, UserS> implements UserService {
}
