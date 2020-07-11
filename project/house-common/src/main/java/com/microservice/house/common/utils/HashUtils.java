package com.microservice.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;


public class HashUtils {

    private static final HashFunction FUNCTION= Hashing.md5();

    private  static final String SALT="microservice.com";

    public static String encryPassword(String password){
        HashCode hashCode=FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
/*
* 加盐的作用，我们的md5有可能被暴力破解的 ，如果用户输入的密码比较简单，
* 这些简单密码的md5值都是固定的，很容易会被暴力破解，所以为了防止，将用
* 户密码和一个字符串一起进行加密
* */