package com.zy.service.impl;

import com.zy.dao.PlayerDao;
import com.zy.domain.Player;
import com.zy.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerDao playerDao;

    @Override
    public Player getPlayerById(Integer id) {
        return playerDao.getPlayerById(id);
    }
}
