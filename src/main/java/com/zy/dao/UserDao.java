package com.zy.dao;

import com.zy.domain.Administrator;
import com.zy.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//
@Mapper
public interface UserDao {
    @Select("select * from user where username=#{username} and password=#{password}")
    public User getUserByUsernameAndPassword(String username, String password);

    @Select("SELECT COUNT(*) FROM USER WHERE user.username=#{username}")
    public Integer selectUsernameCount(String username);

    @Insert("insert into user values(null,#{username},#{password},#{type},#{create_time})")
    public Integer AddUser(User user);

    @Select("SELECT id FROM USER WHERE username = #{username}")
    public Long getIdByUsername(String username);


    ////改改改
    @Select("SELECT * FROM user WHERE id=#{id}")
    public User getUserById(Long id);

    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    public Integer updateUserInfo(User user);

    @Delete("delete from user where id=#{user_id}")
    public Integer deleteAccount(Long user_id);

    @Insert("insert into user values(#{id},#{username},#{password},#{type},#{create_time})")
    public Integer AddUserSteam(User user);

    @Delete("delete from user")
    public Integer deleteAll();

    @Select("select id from user")
    public List<Long> selectUserIdList();


}
