package com.zy.dao;

import com.zy.domain.Administrator;
import com.zy.domain.Player;
import com.zy.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayerDao {
    @Insert("insert into player values(#{id},#{status},#{deposit},#{nick_name},#{name},#{sex},#{phone})")
    public Integer addPlayer(Player player);

    @Select("SELECT * FROM player WHERE id=#{id}")
    public Player getPlayerById(Long id);

    @Update("update player set nick_name=#{nick_name},name=#{name},sex=#{sex},phone=#{phone} where id=#{id}")
    public Integer updatePlayerInfo(Player player);

    //获取游戏的所有id号
    @Select("select id from player")
    public List<Long> selectPlayerIdList();

    @Delete("delete from player where id=#{user_id}")
    public Integer deleteAccount(Long user_id);

    @Insert("INSERT INTO player(id, status, deposit, nick_name, name, sex, phone) VALUES(#{id}, #{status}, #{deposit}, #{nick_name}, #{name}, #{sex}, #{phone})")
    void insertPlayer(Player player);

    @Delete("delete from player")
    public Integer deleteAll();
}
