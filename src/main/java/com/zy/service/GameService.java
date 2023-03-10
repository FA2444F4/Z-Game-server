package com.zy.service;

import com.zy.dao.GameDao;
import com.zy.domain.Game;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
