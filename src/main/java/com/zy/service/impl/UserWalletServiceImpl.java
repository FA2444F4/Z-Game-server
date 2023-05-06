package com.zy.service.impl;

import com.zy.dao.UserWalletDao;
import com.zy.domain.UserWallet;
import com.zy.service.UserWalletService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {
    @Autowired
    private UserWalletDao userWalletDao;

    public UserWallet getUserWallet(Long user_id){
        return userWalletDao.getUserWallet(user_id);
    }

    @Override
    public void initUserWallet(Long user_id) {
        Double balance=0.0;
        Long create_time= DataUtil.timestamp();
        Long update_time= create_time;
        Integer wallet_status=1;
        userWalletDao.initUserWallet(user_id,balance,create_time,update_time,wallet_status);

    }

    @Override
    public void subBalance(Long user_id, Double x) {
        userWalletDao.subBalance(user_id,x);
    }

    @Override
    public void addBalance(Long user_id, Double x) {
        userWalletDao.addBalance(user_id,x);

    }
}
