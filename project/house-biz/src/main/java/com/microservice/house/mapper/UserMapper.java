package com.microservice.house.mapper;

import com.microservice.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    //---------------------------------------------------------------------------------------------
    public List<User>selectUsers();
    //---------------------------------------------------------------------------------------------
    public int insert(User account);
    //---------------------------------------------------------------------------------------------
    public int delect(String email);
    //---------------------------------------------------------------------------------------------
    public void update(User user);
    //---------------------------------------------------------------------------------------------
    public User selectEmailUser(String email);
    //---------------------------------------------------------------------------------------------
    public List<User>selectUserByQuery(User user);
}
