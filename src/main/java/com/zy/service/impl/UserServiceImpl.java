package com.zy.service.impl;

import com.zy.dao.UserDao;
import com.zy.domain.User;
import com.zy.service.UserService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        String password_md5= DataUtil.MD5(password);
        User user=userDao.getUserByUsernameAndPassword(username,password_md5);
        return user;
    }

    @Override
    public Boolean verifyUsernameIsUniQue(String username) {
        Integer count = userDao.selectUsernameCount(username);
        if(count==0){
            //用户名唯一
            return true;
        }
        return false;
    }
}
