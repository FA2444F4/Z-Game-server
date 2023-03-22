package com.zy.service.impl;

import com.zy.controller.Code;
import com.zy.controller.Result;
import com.zy.dao.GameDao;
import com.zy.dao.Game_tagDao;
import com.zy.domain.Game;
import com.zy.service.GameRatingService;
import com.zy.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameDao gameDao;

    @Autowired
    private Game_tagDao game_tagDao;

    @Autowired
    private GameRatingService gameRatingService;

    @Override
    public Boolean addGameAndTag(Game game, List<Integer> selectTag) {
        //添加游戏//得到id
        gameDao.addGame(game);
        Integer game_id=game.getId();
        //添加游戏标签
        for (int i=0;i<selectTag.size();i++){
            game_tagDao.addGame_tag(game_id,selectTag.get(i));
        }
        return true;
    }

    @Override
    public List<Game> getGameListByDeveloperId(Integer id) {
        List<Game> gameList = gameDao.getGameListByDeveloperId(id);

        return gameList;
    }

    @Override
    public Game selectGameById(Integer id) {
        Game game=gameDao.selectGameById(id);
        return game;
    }

    @Override
    public Integer deleteGameByGameId(Integer id) {
        return gameDao.deleteGameByGameId(id);
    }

    @Override
    public ArrayList<HashMap<String, Object>> selectAllGame() {
        ArrayList<HashMap<String,Object>> gameList=new ArrayList<HashMap<String,Object>>();
        //搜索游戏基本信息
        List<Game> games = gameDao.selectAllGame();
        for (Game game : games) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("game" ,game);
            gameList.add(map);
        }
        //搜索游戏评分
        for (HashMap<String, Object> listElement : gameList) {
            Game game= (Game)listElement.get("game");
            Integer gameId=game.getId();
            //统计游戏所有评论的评分
            //如果没有则为-1
            //先搜索有几条评论
            Integer ratingNum=gameRatingService.countRatingNum(gameId);
            if (ratingNum==0){//如果没有评论
                listElement.put("rating",-1);
            }else {//如果有评论
                //按评分排序时 5 4 3 2 1 //未评分数据分开列
                //根据gameId搜索游戏评分
                Double rating = gameRatingService.countRatingAvg(gameId);
                listElement.put("rating",rating);
            }
        }
        //把表给前端
        return gameList;
    }
}
