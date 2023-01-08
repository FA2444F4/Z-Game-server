package com.zy.dao;

import com.zy.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from user where username=#{username} and password=#{password}")
    public User getUserByUsernameAndPassword(String username, String password);

    @Select("SELECT COUNT(*) FROM USER WHERE user.username=#{username}")
    public Integer selectUsernameCount(String username);
}
