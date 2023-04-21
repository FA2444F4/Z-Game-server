package com.zy.util;

import com.zy.dao.GameDao;
import com.zy.dao.PlayerDao;
import com.zy.dao.UserDao;
import com.zy.domain.GameRating;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class SqlUtil {
    @Autowired
    private GameRating gameRating;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private GameDao gameDao;


}
