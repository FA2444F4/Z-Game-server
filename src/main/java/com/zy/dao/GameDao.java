package com.zy.dao;

import com.zy.domain.Game;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GameDao {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into game values(null,#{name},#{developer_id},#{price},#{create_time},#{description},#{header_image},#{screenshot1},#{screenshot2},#{screenshot3},#{is_exist})")
    public Integer addGame(Game game);

    @Select("select * from game where developer_id = #{id}")
    public List<Game> getGameListByDeveloperId(Integer id);

    @Delete("delete from game where id=#{id}")
    public Integer deleteGameByGameId(Integer id);


    @Select("select * from game where id=#{id}")
    public Game selectGameById(Integer id);


    @Select("select * from game")
    public List<Game> selectAllGame();

    @Select("select * from game where name like CONCAT('%',#{input},'%')")
    public List<Game> selectGameByInput(String input);

    //获取游戏的所有id号
    @Select("select id from game")
    public List<Integer> selectGameIdList();




}
