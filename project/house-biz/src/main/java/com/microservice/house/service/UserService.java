package com.microservice.house.service;

import com.microservice.house.common.model.User;

import java.util.List;

public interface UserService {
    public List<User>getUsers();


    public boolean addAccount(User account);

    public boolean enable(String key);
}
