package com.cwy.educationaladministrationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cwy.educationaladministrationsystem.model.UserS;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface userMapper extends BaseMapper<UserS> {
}
