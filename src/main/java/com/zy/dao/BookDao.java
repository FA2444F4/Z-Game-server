package com.zy.dao;

import com.zy.domain.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper //告诉boot这是一个mybatis的mapper
public interface BookDao {
    //mybatis注解开发

    //@Insert("insert into tbl_book values(null,#{type},#{name},#{description}")
    @Insert("insert into tbl_book (type,name,description) values(#{type},#{name},#{description})")
    public int save(Book book);

    @Update("update tbl_book set type=#{type},name=#{name},description=#{description} where id=#{id}")
    public int update(Book book);

    @Delete("delete from tbl_book where id=#{id}")
    public int delete(Integer id);

    @Select("select * from tbl_book where id=#{id}")
    public Book getById(Integer id);

    @Select("select * from tbl_book")
    public List<Book> getAll();
}
