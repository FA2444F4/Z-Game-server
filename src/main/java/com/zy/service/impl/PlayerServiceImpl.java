package com.zy.service.impl;

import com.zy.dao.PlayerDao;
import com.zy.dao.UserDao;
import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.PlayerService;
import com.zy.util.DataUtil;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Player getPlayerById(Integer id) {
        return playerDao.getPlayerById(id);
    }

    @Override
    public Boolean updatePlayerInfo(User user, Player player) {
        user.setPassword(DataUtil.MD5(user.getPassword()));
        userDao.updateUserInfo(user);
        playerDao.updatePlayerInfo(player);
        return true;
    }

    @Override
    public Param getPlayerInfo(Integer id) {
        User user = userDao.getUserById(id);
        Player player = playerDao.getPlayerById(id);
        Param param = new Param();
        param.setUser(user);
        param.setPlayer(player);

        return param;
    }

    @Override
    public List<Integer> selectPlayerIdList() {
        return playerDao.selectPlayerIdList();
    }
}
