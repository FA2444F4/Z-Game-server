package com.zy.service;

import com.zy.domain.Player;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlayerService {
    /**
     * 根据id查找玩家信息
     * @param id
     * @return
     */
    public Player getPlayerById(Integer id);
}
