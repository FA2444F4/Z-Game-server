package com.zy.dao;

import com.zy.domain.Player;
import com.zy.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerDao {
    @Insert("insert into player values(#{id},#{status},#{deposit},#{nick_name},#{name},#{sex},#{phone})")
    public Integer AddPlayer(Player player);
}
