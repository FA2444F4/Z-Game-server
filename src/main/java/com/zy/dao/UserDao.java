package com.zy.dao;

import com.zy.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from user where username=#{username} and password=#{password}")
    public User getUserByUsernameAndPassword(String username, String password);

    @Select("SELECT COUNT(*) FROM USER WHERE user.username=#{username}")
    public Integer selectUsernameCount(String username);

    @Insert("insert into user values(null,#{username},#{password},#{type},#{create_time})")
    public Integer AddUser(User user);

    @Select("SELECT id FROM USER WHERE username = #{username}")
    public Integer getIdByUsername(String username);
}
