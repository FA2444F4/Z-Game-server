package com.zy.service.impl;

import com.zy.dao.GameDao;
import com.zy.dao.Game_tagDao;
import com.zy.domain.Game;
import com.zy.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameDao gameDao;

    @Autowired
    private Game_tagDao game_tagDao;

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
}
