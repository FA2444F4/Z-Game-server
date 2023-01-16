package com.zy.service;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import org.springframework.transaction.annotation.Transactional;

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

}
