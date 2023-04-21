package com.zy.service.impl;

import com.zy.dao.GameDao;
import com.zy.dao.GameRatingDao;
import com.zy.domain.GameRating;
import com.zy.service.GameRatingService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameRatingServiceImpl implements GameRatingService {
    @Autowired
    private GameRatingDao gameRatingDao;

    @Autowired
    private GameDao gameDao;


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
        return gameRatingDao.selectRatingCountFromOnePlayer(player_id) >= 5;
    }

    @Override
    public void createOrUpdatePlayerRating(ArrayList<Map<String, Integer>> ratingList, Integer player_id) {
        for (Map<String, Integer> element : ratingList) {
            Integer game_id = element.get("id");
            Integer rating = element.get("rating");
            //先看是否评价过
            Integer count = gameRatingDao.ifPlayerHadRatingThisGame(player_id, game_id);
            if (count == 1) {//如果评价过则修改
                //修改评论
                gameRatingDao.updateRating(rating, player_id, game_id);
            } else if (rating != -1) {//如果没评价过则新增评论
                gameRatingDao.insertRating(game_id, player_id, rating, "", DataUtil.timestamp(), 1);
            }

        }
    }

    @Override
    public ArrayList<Map<String, Integer>> getGameIdAndRatingFromOnePlayer(Integer player_id) {
        //初始化该用户的map<int 游戏id,int 评分>
        ArrayList<Map<String, Integer>> currentPlayerRatingMapList = new ArrayList<Map<String, Integer>>();
        List<Integer> gameIdList = gameDao.selectGameIdList();
        for (Integer id : gameIdList) {
            HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
            tempMap.put("game_id", id);
            tempMap.put("rating", 0);
            currentPlayerRatingMapList.add(tempMap);
        }
        //把用户数据放进去
        List<Map<String, Integer>> gameIdRatingList = gameRatingDao.getGameIdAndRatingFromOnePlayer(player_id);
        for (Map<String, Integer> tempMap : gameIdRatingList) {
            Integer rating = tempMap.get("rating");
            if (rating != -1 && rating != 0) {//有必要计入数据
                Integer gameId = tempMap.get("game_id");
                for (Map<String, Integer> element : currentPlayerRatingMapList) {
                    if (element.get("game_id") == gameId) {
                        element.put("rating",rating);
                    }
                }
            }
        }



        return currentPlayerRatingMapList;
    }

    @Override
    public List<GameRating> selectRatingListFromOnePlayer(Integer player_id) {
        return gameRatingDao.selectRatingListFromOnePlayer(player_id);
    }

    @Override
    public GameRating selectRatingByPlayerIdAndGameId(Integer player_id, Integer game_id) {
        return gameRatingDao.selectRatingByPlayerIdAndGameId(player_id,game_id);
    }

    @Override
    public List<GameRating> selectRatingListByGameId(Integer game_id) {
        return gameRatingDao.selectRatingListByGameId(game_id);
    }

    @Override
    public Integer addPlayerGameRating(Integer game_id, Integer player_id, Integer rating, String comment) {
        Long create_time=DataUtil.timestamp();
        Integer is_exist=1;
        return gameRatingDao.insertRating(game_id,player_id,rating,comment,create_time,is_exist);
    }

    @Override
    public HashMap<Integer, Integer> getGameRatingGrad(Integer game_id) {
        HashMap<Integer, Integer> resMap = new HashMap<>();
        for (int i=1;i<=5;i++){
            resMap.put(i,gameRatingDao.getARatingNumForGame(game_id,i));
        }

        return resMap;
    }
}
