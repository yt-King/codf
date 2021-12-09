package com.zufe.codf.dao;

import com.zufe.codf.model.User;
import com.zufe.codf.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 应涛
 * @date 2021/8/27
 * @function：
 */
@Mapper
@Repository
public interface UserMapper {
    int insertUser(User user);
    int login(UserDto userDto);
}
