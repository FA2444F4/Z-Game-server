package com.zy.service.impl;

import com.zy.dao.DeveloperDao;
import com.zy.dao.PlayerDao;
import com.zy.dao.UserDao;
import com.zy.domain.Developer;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.UserService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private DeveloperDao developerDao;


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

    @Override
    public Boolean addUserAndPlayer(User user, Player player) {
        userDao.AddUser(user);
        Integer id = userDao.getIdByUsername(user.getUsername());
        player.setId(id);
        playerDao.addPlayer(player);

        //懒得判断了
        return null;
    }

    @Override
    public Boolean addUserAndDeveloper(User user, Developer developer) {
        userDao.AddUser(user);
        Integer id = userDao.getIdByUsername(user.getUsername());
        developer.setId(id);
        developerDao.addDeveloper(developer);

        //懒得判断了
        return null;
    }

    @Override
    public Integer selectUserIdByUsername(String username) {
        return userDao.getIdByUsername(username);
    }
}
