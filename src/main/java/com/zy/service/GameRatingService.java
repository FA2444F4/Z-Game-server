package com.zy.service;

import com.zy.domain.GameRating;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public interface GameRatingService {
    /**
     * 根据游戏id统计游戏平均得分
     * @param game_id
     * @return
     */
    public Double countRatingAvg(Integer game_id);

    /**
     * 根据游戏id搜索游戏评论数量
     * @param game_id
     * @return
     */
    public Integer countRatingNum(Integer game_id);


    public Boolean ifPlayerRatingNumberEnough(Integer player_id);

    /**
     * 没有评分则创建,有则修改
     */
    public void createOrUpdatePlayerRating(ArrayList<Map<String,Integer>> ratingList,Integer player_id);

    /*
    根据玩家id搜他的游戏评分数据
     */
    public ArrayList<Map<String,Integer>> getGameIdAndRatingFromOnePlayer(Integer player_id);


    public List<GameRating> selectRatingListFromOnePlayer(Integer player_id);

    //根据玩家id和游戏id搜索评分
    public GameRating selectRatingByPlayerIdAndGameId(Integer player_id,Integer game_id);

    public List<GameRating> selectRatingListByGameId(Integer game_id);

    public Integer addPlayerGameRating(Integer game_id,Integer player_id,Integer rating,String comment);

    public HashMap<Integer,Integer> getGameRatingGrad(Integer game_id);


}
