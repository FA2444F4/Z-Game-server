package com.zy.dao;

import com.zy.domain.Administrator;
import com.zy.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdministratorDao {
    @Select("SELECT * FROM administrator WHERE id=#{id}")
    public Administrator getAdministratorById(Long id);

    @Update("update administrator set nick_name=#{nick_name} where id=#{id}")
    public Integer updateAdministratorInfo(Administrator administrator);
}
