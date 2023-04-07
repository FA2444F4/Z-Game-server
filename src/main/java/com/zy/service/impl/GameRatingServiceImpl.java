package com.zy.service.impl;

import com.zy.dao.GameRatingDao;
import com.zy.service.GameRatingService;
import com.zy.util.DataUtil;
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
        for (Map<String, Integer> element : ratingList) {
            Integer game_id=element.get("id");
            Integer rating=element.get("rating");
            //先看是否评价过
            Integer count = gameRatingDao.ifPlayerHadRatingThisGame(player_id, game_id);
            if(count==1){//如果评价过则修改
                //修改评论
                gameRatingDao.updateRating(rating,player_id,game_id);
            }else if(rating!=-1) {//如果没评价过则新增评论
                gameRatingDao.insertRating(game_id,player_id,rating,"", DataUtil.timestamp(),1);
            }

        }
    }
}
