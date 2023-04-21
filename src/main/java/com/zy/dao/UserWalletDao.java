package com.zy.dao;

import com.zy.domain.Tag;
import com.zy.domain.User;
import com.zy.domain.UserWallet;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PutMapping;

@Mapper
public interface UserWalletDao {
    @Select("select * from user_wallet where user_id=#{user_id}")
    public UserWallet getUserWallet(Integer user_id);

    @Insert("insert into user_wallet values(#{user_id},#{balance},#{create_time},#{update_time},#{wallet_status})")
    public void initUserWallet(Integer user_id,Double balance,Long create_time,Long update_time,Integer wallet_status);

    //扣钱
    @Update("update user_wallet set balance=balance-#{x} where user_id=#{user_id}")
    public void subBalance(Integer user_id, Double x);

    //加钱
    @Update("update user_wallet set balance=balance+#{x}  where user_id=#{user_id}")
    public void addBalance(Integer user_id,Double x );

    //删除账号
    @Delete("delete from user_wallet where user_id=#{user_id}")
    public void deleteAccount(){

    }

}
