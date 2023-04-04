package com.zy.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
}
