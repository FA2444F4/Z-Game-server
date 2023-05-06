package com.zy.service;

import com.zy.domain.User;
import com.zy.domain.UserWallet;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserWalletService {
    public UserWallet getUserWallet(Long user_id);

    public void initUserWallet(Long user_id);

    //扣钱
    public void subBalance(Long user_id, Double x);

    //加钱
    public void addBalance(Long user_id,Double x );
}
