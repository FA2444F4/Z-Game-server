package com.zy.service;

import com.zy.dao.GameDao;
import com.zy.domain.Game;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public interface GameService {
    /**
     * 新增游戏,同时添加上标签
     * @param game
     * @param selectTag
     * @return
     */
    public Boolean addGameAndTag(Game game, List<Integer> selectTag);

    /**
     * 根据开发商id获取开发商旗下所有游戏,返回列表
     * @param id
     * @return
     */
    public List<Game> getGameListByDeveloperId(Integer id);

    /**
     * 根据游戏id搜索游戏
     * @param id
     * @return
     */
    public Game selectGameById(Integer id);

    public Integer deleteGameByGameId(Integer id);

    /**
     * 搜索游戏信息
     * 和    游戏评分
     *  其他待定
     */
    public ArrayList<HashMap<String,Object>> selectAllGame();

    //根据用户输入词模糊查询游戏
    public ArrayList<HashMap<String,Object>> selectGameByInput(String input);


    public ArrayList<HashMap<String,Object>> getGameListByRatingDescending();
    public ArrayList<HashMap<String,Object>> getGameListByRatingAscending();

    public ArrayList<HashMap<String,Object>> getGameListByPriceDescending();
    public ArrayList<HashMap<String,Object>> getGameListByPriceAscending();

    public ArrayList<HashMap<String,Object>> getWaitingRatingGameList();

    public List<Integer> selectGameIdList();

    public ArrayList<HashMap<String,Object>> getRecommendGameListByGameIdList(List<Integer> gameCountList);
}
