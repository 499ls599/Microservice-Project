package com.microservice.house.mapper;

import com.microservice.house.common.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
public class UserMapperTest {

   @Autowired
   private UserMapper userMapper;

    @Test
    public void selectEmailUser() {
        String email="springboot_project@163.com";
        User user = userMapper.selectEmailUser(email);
    }

    @Test
    public void selectUsers(){
        List<User> userList = userMapper.selectUsers();
        for (User user:userList) {
            System.out.println("user = " + user);
        }
    }
}