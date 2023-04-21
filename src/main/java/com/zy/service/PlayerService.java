package com.zy.service;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface PlayerService {
    /**
     * 根据id查找玩家信息
     * @param id
     * @return
     */
    public Player getPlayerById(Integer id);

    /**
     * 修改玩家的player和user
     * @param user
     * @param player
     * @return
     */
    public Boolean updatePlayerInfo(User user, Player player);

    /**
     * 根据id搜索玩家所有信息并封装在param里
     * @param id
     * @return
     */
    public Param getPlayerInfo(Integer id);

    public List<Integer> selectPlayerIdList();

    public Map<String, Object> selectPlayerGameByOne(Integer player_id, Integer game_id);

    //看玩家是否买了这个游戏,看玩家是否评分
    public Map<String,Integer> buyAndRatingButtonInfo(Integer player_id, Integer game_id);

}
