package com.zy.dao;

import com.zy.domain.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagDao {
    @Insert("insert into tag values(null,#{name},#{description})")
    public Integer addTag(Tag tag);

    @Select("select * from tag where name=#{name}")
    public Tag selectTagByName(String name);

    @Select("select * from tag")
    public List<Tag> getAllTag();

    @Delete("delete from tag where id=#{id}")
    public Integer deleteTag(Integer id);

    @Select("select * from tag where name like CONCAT('%',#{name},'%')")
    public List<Tag> selectTagByNameLike(String name);

    @Update("update tag set name=#{name},description=#{description} where id=#{id}")
    public Integer updateTag(Tag tag);

    //SELECT tag.`name` FROM tag,game_tag WHERE game_tag.`game_id`=1 AND game_tag.`tag_id`=tag.`id`
    @Select("SELECT tag.`name` FROM tag,game_tag WHERE game_tag.`game_id`=#{id} AND game_tag.`tag_id`=tag.`id`")
    public List<String> selectTagNameByGameId(Integer id);

    @Delete("delete from tag where game_id=#{id}")
    public Integer deleteTagByGameId(Integer id);

    //标签是否存在
    @Select("select count(*) from tag where id=#{id}")
    public Integer ifTagExist(Integer id);
    //插入带id的标签
    @Insert("insert into tag values(#{id},#{name},#{description})")
    public Integer addTagWhichHaveId(Tag tag);

    @Select("select tag_id from game_tag where game_id=#{game_id}")
    public List<Integer> selectTagIdListFromGame(Integer game_id);

    @Select("select id from tag")
    public List<Integer> selectTagIdList();



}
