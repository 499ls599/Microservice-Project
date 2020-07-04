package com.microservice.house.mapper;

import com.microservice.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User>selectUsers();
}
