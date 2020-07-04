package com.microservice.house.controller;

import com.microservice.house.common.model.User;
import com.microservice.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Description   :  这个类用于测试连接Mybatis查询数据展示到浏览器
 * @ Author        :  1910959369@qq.com
 * @ CreateDate    :  2020/7/3 19:34
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("user")
    public List<User>getUsers(){
        return userService.getUsers();
    }
}
