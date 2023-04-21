package com.zy.dao;

import com.zy.domain.Essay;
import com.zy.domain.EssayMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EssayDao {
    @Insert("insert into essay (id,publisher_id,publisher_name,create_time,essay_name,essay_comment,likes) values(null,#{publisher_id},#{publisher_name},#{create_time},#{essay_name},#{essay_comment},#{likes})")
    public Integer addEssay(Integer id,
                            Integer publisher_id,
                            String publisher_name,
                            Long create_time,
                            String essay_name,
                            String essay_comment,
                            Integer likes);

    @Select("select * from essay")
    public List<Essay> selectAllEssay();

    @Select("select * from essay ORDER BY id DESC")
    public List<Essay> selectAllEssayIdReverseOrder();

    @Select("select * from essay ORDER BY likes DESC")
    public List<Essay> selectAllEssayLikes();

    @Select("select id from essay where essay_name like CONCAT('%',#{input},'%')")
    public List<Integer> selectIdByEssayName(String input);

    @Select("select id from essay where essay_comment like CONCAT('%',#{input},'%')")
    public List<Integer> selectIdByEssayComment(String input);

    @Select("select max(id) from essay")
    public Integer selectMaxId();

    @Select("select * from essay where id=#{id}")
    public Essay selectEssayById(Integer id);

    @Select("select * from essay where publisher_id=#{user_id}")
    public List<Essay> selectEssayByPublisherId(Integer user_id);

    @Select("select * from essay where id=#{id}")
    public Essay getEssayById(Integer id);
    @Select("select * from essay_message where essay_id=#{essay_id}")
    public List<EssayMessage> getMessageByEssayId(Integer essay_id);

    @Update("update essay set likes=likes+1 where id=#{essay_id}")
    public Integer addLikes(Integer essay_id);

    @Insert("insert into essay_message (id,messenger_id,messenger_name,create_time,essay_id,message) values(null,#{messenger_id},#{messenger_name},#{create_time},#{essay_id},#{message})")
    public Integer addMessage(
                            Integer messenger_id,
                            String  messenger_name,
                            Long create_time,
                            Integer essay_id,
                            String message);




    @Delete("delete from essay_message where messenger_id=#{user_id}")
    public Integer deleteAccountMessage(Integer user_id);

    @Delete("delete from essay where publisher_id=#{user_id}")
    public Integer deleteAccountEssay(Integer user_id);


}
