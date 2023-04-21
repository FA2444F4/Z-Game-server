package com.zy.dao;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface PlayerGameDao {
    @Select("select * from player_game where player_id=#{player_id} and game_id=#{game_id}")
    public Map<String, Object> selectPlayerGameByOne(Integer player_id, Integer game_id);

    //获取拥有拥有的游戏id
    @Select("select game_id from player_game where player_id=#{player_id}")
    public List<Integer> selectPlayerGameId(Integer player_id);


    @Select("select is_have from player_game where player_id=#{player_id} and game_id=#{game_id}")
    public Integer selectGameIsBuy(Integer player_id, Integer game_id);

    //增加游戏
    @Insert("insert into player_game values(#{player_id},#{game_id},#{create_time},#{status},#{is_have})")
    public Integer addPlayerGame(Integer player_id,Integer game_id,Long create_time,Integer status,Integer is_have);

    @Delete("delete from player_game where player_id=#{user_id}")
    public Integer deleteAccount(Integer user_id);

}
