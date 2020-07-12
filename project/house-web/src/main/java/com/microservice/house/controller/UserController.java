package com.microservice.house.controller;

import com.microservice.house.common.constants.CommonConstants;
import com.microservice.house.common.model.User;
import com.microservice.house.common.result.ResultMsg;
import com.microservice.house.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ Description   :  这个类用于测试连接Mybatis查询数据展示到浏览器
 * @ Author        :  1910959369@qq.com
 * @ CreateDate    :  2020/7/3 19:34
 * @author 17806530484
 */

//---------------------------------------------------------------------------------------------
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    //-------------------------------------------用户注册实现--------------------------------------------------
    /*
     * 注册提交
     * 1：注册验证
     * 2：发送邮件
     * 3：验证失败重定向到注册页面
     *
     * 注册页或企业：根据account对象为依据判断是否注册页面获取请求
     * */
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

    //-------------------------------------------用户注册激活实现--------------------------------------------------
    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
        }
    }
    //-------------------------------------------用户登录接口实现-------------------------------------------------
    @RequestMapping("accounts/signin")
    public String signin(HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String target=request.getParameter("target");
        if(username==null||password==null){
            request.setAttribute("target",target);
            return "/user/accounts/signin";
        }
        User user=userService.auth(username,password);
        if(user==null){
            return "/user/accounts/signin?"+"target"+target+"&username"+username+"&"+ResultMsg.errorMsg("用户名错误").asUrlParams();
        }else{
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,user);
            //return StringUtils.isBlank(target)?"redirect:"+target:"redirect:/index";
            if(!StringUtils.isBlank(target)){
                return "redirect:/"+target;
            }else{
                return "redirect:/index";
            }
        }
    }
    //-------------------------------------------用户登出接口实现-------------------------------------------------
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }
}
