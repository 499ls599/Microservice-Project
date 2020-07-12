package com.microservice.house.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.microservice.house.common.model.User;
import com.microservice.house.mapper.UserMapper;
import com.microservice.house.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//-------------------------------------------------------------注册邮件发送-------------------------------------------------------------------
@Service
public class MailServiceImpl implements MailService {

    //------------------------------------------------------------本地缓存（guvav cache对象）--------------------------------------------------------------------
    //本地缓存（guava cache对象）
    /*
     *最大存储的空间 maximumSize(100)，超过这个数字会进行剔除
     *
     * 任务过期时间.expireAfterAccess(15, TimeUnit.MINUTES)，如果注册之后超过这个时间没有发送激活连接，要进行重新注册
     *
     *过期的时候要进行的操作.removalListener(new RemovalListener<String,String>() ，7 要是还没有注册 要去做数据库删除
     * */
    private final Cache<String,String> registerCache= CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String,String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> removalNotification) {
                    userMapper.delect(removalNotification.getValue());
                }
            }).build();

    //--------------------------------------------------------------------------------------------------------------------------------
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String from;


    @Value("${domain.name}")
    private String domain;

    //--------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(title);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    /*
     * 1:缓存key-email的关系
     * 2：借助spring email发送邮件
     * 3：借助异步框架进行异步操作
     * */
    //加入Asunc注解，Spring会将这个任务加到线程池去执行
    @Override
    @Async
    public void registerNotify(String email) {
        //随机生成一个十位的字符串
        String randomKey= RandomStringUtils.randomAlphabetic(10);
        //缓存语
        registerCache.put(randomKey,email);
        String url= "http://"+domain+"/accounts/verify?key="+randomKey;
        sendMail("#激活邮件",url,email);
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean enable(String key) {
        //通过key获取缓存中的value
        String email=registerCache.getIfPresent(key);
        //isBlank一次性进行三次校验，a:是否为空，b:是否为"",c:是否为"   "
        if(StringUtils.isBlank(email)){
            return false;
        }
        userMapper.update(userMapper.selectEmailUser(email));
        System.out.println("userMapper = " + userMapper.selectEmailUser(email));
        //解除绑定关系
        //registerCache.invalidate(key);
        return true;
    }
}
