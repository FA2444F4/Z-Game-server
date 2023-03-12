package com.zy;

import com.zy.dao.GameDao;
import com.zy.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameTest {
    @Autowired
    private GameService gameService;
    @Autowired
    private GameDao gameDao;

    @Test
    void aa(){
        //gameService.getGameListByDeveloperId(100002);
        gameDao.getGameListByDeveloperId(100002);
    }
}
