package com.microservice.house.controller;

import com.microservice.house.common.model.User;
import com.microservice.house.common.result.ResultMsg;
import com.microservice.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Description   :  这个类用于测试连接Mybatis查询数据展示到浏览器
 * @ Author        :  1910959369@qq.com
 * @ CreateDate    :  2020/7/3 19:34
 * @author 17806530484
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @ Description   :  注册提交
     * 注册提交：1：注册验证   2：发送邮件    3：验证重定向到注册页面
     * 注册页获取：根据account对象为依据判断是否注册页面获取请求
     * @ Author        :  1910959369@qq.com
     * @ CreateDate    :  2020/7/5 14:11
     */

    @RequestMapping("accounts/register")
    public String accountRegister(User account, ModelMap modelMap){
        //如果获取到的对象为null或者对象的名字为null，跳转到注册页面
        if(account==null||account.getName()==null){
            modelMap.put("email",account.getEmail());
            return "/user/accounts/register";
        }

        //用户验证
        ResultMsg resultMsg = UserHelper.validata(account);

        if(resultMsg.isSuccess()&&userService.addAccount(account)){
            modelMap.put("email",account.getEmail());
            return "/user/accounts/registerSubmit";
        }else{
            return "redirect:/accounts/register?"+resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
        }
    }
}
