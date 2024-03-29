package com.zy.dao;

import com.zy.domain.Developer;
import com.zy.domain.Player;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DeveloperDao {
    //新增开发商
    @Insert("insert into developer values(#{id},#{deposit},#{name},#{phone},#{description})")
    public Integer addDeveloper(Developer developer);

    @Select("SELECT * FROM developer WHERE id=#{id}")
    public com.zy.domain.Developer getDeveloperById(Long id);

    @Update("update developer set deposit=#{deposit},name=#{name},phone=#{phone},description=#{description} where id=#{id}")
    public Integer updateDeveloperInfo(Developer developer);
}
