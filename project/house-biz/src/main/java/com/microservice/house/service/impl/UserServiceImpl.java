package com.microservice.house.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.microservice.house.common.model.User;
import com.microservice.house.common.utils.BeanHelper;
import com.microservice.house.common.utils.HashUtils;
import com.microservice.house.mapper.UserMapper;
import com.microservice.house.service.FileService;
import com.microservice.house.service.MailService;
import com.microservice.house.service.UserService;
import com.mysql.cj.util.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;



    @Override
    public List<User> getUsers() {
        return userMapper.selectUsers();
    }

    /*
     * 1;插入数据库，非激活状态；密码加盐md6；保存头像到本地
     * 2：生成key，绑定email
     * 3：发送邮件给用户
     * */

    //这个事务只有在UserController调用addAccount事务才会生效
    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean addAccount(User account){
        //密码进行加盐操作
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        //将图像保存到本地
        //用list可以返回一个也可以多个
        List<String>imgList=fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if(!imgList.isEmpty()){
            //拿到第一个
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account,User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insert(account);
        //发送邮件
        mailService.registerNotify(account.getEmail());
        return true;
    }

    @Override
    public boolean enable(String key) {
        return mailService.enable(key);
    }


}
