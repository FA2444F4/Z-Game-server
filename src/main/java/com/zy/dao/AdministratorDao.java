package com.zy.dao;

import com.zy.domain.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdministratorDao {
    @Select("SELECT * FROM administrator WHERE id=#{id}")
    public Administrator getAdministratorById(Integer id);
}
