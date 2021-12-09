package com.zufe.codf.service;

import com.zufe.codf.dao.UserMapper;
import com.zufe.codf.model.User;
import com.zufe.codf.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 应涛
 * @date 2021/8/27
 * @function：
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public int insert(User user){
        return userMapper.insertUser(user);
    }
    public int login(UserDto user){
        return userMapper.login(user);
    }
}
