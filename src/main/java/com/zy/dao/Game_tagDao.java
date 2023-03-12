package com.zy.dao;

import com.zy.domain.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Game_tagDao {
    @Insert("insert into game_tag values(#{game_id},#{tag_id})")
    public Integer addGame_tag(Integer game_id,Integer tag_id);
}
