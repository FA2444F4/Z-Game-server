package com.zy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GameRatingDao {
    @Select("select avg(rating) from game_rating where game_id=#{game_id}")
    public Double countRatingAvg(Integer game_id);

    @Select("select count(*) from game_rating where game_id=#{game_id}")
    public Integer countRatingNum(Integer game_id);

    @Select("select count(*) from game_rating where player_id=#{player_id}")
    public Integer selectRatingCountFromOnePlayer(Integer player_id);
}
