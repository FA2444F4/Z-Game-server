package com.zy.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GameRatingDao {
    @Select("select avg(rating) from game_rating where game_id=#{game_id}")
    public Double countRatingAvg(Integer game_id);

    @Select("select count(*) from game_rating where game_id=#{game_id}")
    public Integer countRatingNum(Integer game_id);

    @Select("select count(*) from game_rating where player_id=#{player_id}")
    public Integer selectRatingCountFromOnePlayer(Integer player_id);

    @Select("select count(*) from game_rating where player_id=#{player_id} and game_id=#{game_id}")
    public Integer ifPlayerHadRatingThisGame(Integer player_id,Integer game_id);

    //修改评论的评分
    @Update("update game_rating set rating=#{rating} where player_id=#{player_id} and game_id=#{game_id}")
    public Integer updateRating(Integer rating,Integer player_id,Integer game_id);

    //新增评分
    @Insert("insert into game_rating values(#{game_id},#{player_id},#{rating},#{comment},#{create_time},#{is_exist})")
    public Integer insertRating(Integer game_id,Integer player_id,Integer rating,String comment,Long create_time,Integer is_exist);

}
