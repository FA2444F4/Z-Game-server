package com.zy.dao;

import com.zy.domain.GameRating;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Select("select game_id,rating from game_rating where player_id=#{player_id}")
    public List<Map<String,Integer>> getGameIdAndRatingFromOnePlayer(Integer player_id);

    //根据玩家id搜索所有评论
    @Select("select * from game_rating where player_id=#{player_id}")
    public List<GameRating> selectRatingListFromOnePlayer(Integer player_id);


    //根据玩家id搜索所有评论
    @Select("select rating from game_rating where player_id=#{player_id} and game_id=#{game_id}")
    public Integer selectIsRating(Integer player_id,Integer game_id);

    @Select("select * from game_rating where player_id=#{player_id} and game_id=#{game_id}")
    public GameRating selectRatingByPlayerIdAndGameId(Integer player_id, Integer game_id);

    //根据游戏id搜索所有评论
    @Select("select * from game_rating where game_id=#{game_id}")
    public List<GameRating> selectRatingListByGameId(Integer game_id);


//    public Integer addPlayerGameRating(Integer game_id,Integer player_id,Integer rating,String comment,Long create_time,Integer is_exist);

    @Delete("delete from game_rating where player_id=#{user_id}")
    public Integer deleteAccount(Integer user_id);

    @Select("SELECT COUNT(*) FROM game_rating WHERE rating=#{rating} AND game_id=#{game_id}")
    public Integer getARatingNumForGame(Integer game_id,Integer rating);

}
