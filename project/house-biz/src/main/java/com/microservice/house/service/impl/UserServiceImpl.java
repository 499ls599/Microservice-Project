package com.microservice.house.service.impl;

import com.microservice.house.common.model.User;
import com.microservice.house.mapper.UserMapper;
import com.microservice.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.selectUsers();
    }
}
