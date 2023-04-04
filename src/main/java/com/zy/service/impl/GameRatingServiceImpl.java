package com.zy.service.impl;

import com.zy.dao.GameRatingDao;
import com.zy.service.GameRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class GameRatingServiceImpl implements GameRatingService {
    @Autowired
    private GameRatingDao gameRatingDao;


    @Override
    public Double countRatingAvg(Integer game_id) {
        return gameRatingDao.countRatingAvg(game_id);
    }

    @Override
    public Integer countRatingNum(Integer game_id) {
        return gameRatingDao.countRatingNum(game_id);
    }

    @Override
    public Boolean ifPlayerRatingNumberEnough(Integer player_id) {
        return gameRatingDao.selectRatingCountFromOnePlayer(player_id)>=5;
    }

    @Override
    public void createOrUpdatePlayerRating(ArrayList<Map<String, Integer>> ratingList, Integer player_id) {

    }
}
