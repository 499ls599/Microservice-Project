package com.microservice.house.controller;

import com.microservice.house.common.model.User;
import com.microservice.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//---------------------------------------------------------------------------------------------
@Controller
public class HelloController
{
    //---------------------------------------------------------------------------------------------
    @Autowired
    private UserService userService;

    //---------------------------------------------------------------------------------------------
    @RequestMapping("hello")
    public String hello(ModelMap model){
        List<User>users = userService.getUsers();
        User one = users.get(0);
        //TODO  为了便于测试500错误页面，获取数据为null时跳转
       /* if(one!=null){
            //非法参数异常
            throw new IllegalArgumentException();
        }*/
        System.out.println("one = " + one);
        model.put("user",one);
        return "hello";
    }

    //---------------------------------------------------------------------------------------------
    @RequestMapping("index")
    public String index(){
        return "homepage/index";
    }
}
