package com.zy.service;

import com.zy.domain.User;
import com.zy.domain.UserWallet;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserWalletService {
    public UserWallet getUserWallet(Integer user_id);

    public void initUserWallet(Integer user_id);

    //扣钱
    public void subBalance(Integer user_id, Double x);

    //加钱
    public void addBalance(Integer user_id,Double x );
}
