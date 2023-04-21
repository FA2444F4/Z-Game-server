package com.zy.service.impl;

import com.zy.dao.GameRatingDao;
import com.zy.dao.PlayerDao;
import com.zy.dao.PlayerGameDao;
import com.zy.dao.UserDao;
import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.PlayerService;
import com.zy.util.DataUtil;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private PlayerGameDao playerGameDao;

    @Autowired
    private GameRatingDao gameRatingDao;

    @Override
    public Player getPlayerById(Integer id) {
        return playerDao.getPlayerById(id);
    }

    @Override
    public Boolean updatePlayerInfo(User user, Player player) {
        user.setPassword(DataUtil.MD5(user.getPassword()));
        userDao.updateUserInfo(user);
        playerDao.updatePlayerInfo(player);
        return true;
    }

    @Override
    public Param getPlayerInfo(Integer id) {
        User user = userDao.getUserById(id);
        Player player = playerDao.getPlayerById(id);
        Param param = new Param();
        param.setUser(user);
        param.setPlayer(player);

        return param;
    }

    @Override
    public List<Integer> selectPlayerIdList() {
        return playerDao.selectPlayerIdList();
    }

    @Override
    public Map<String, Object> selectPlayerGameByOne(Integer player_id, Integer game_id) {
        return playerGameDao.selectPlayerGameByOne(player_id,game_id);
    }

    @Override
    public Map<String, Integer> buyAndRatingButtonInfo(Integer player_id, Integer game_id) {
        Integer isRating =gameRatingDao.selectIsRating(player_id,game_id);
        Integer isBuy=playerGameDao.selectGameIsBuy(player_id,game_id);
        ///
        if(player_id==100001){
            System.out.println(isRating);
            System.out.println(isBuy);
        }
        ///
        HashMap<String,Integer> map = new HashMap<>();
        if(isRating==null||isRating==0){

            map.put("rating_button",0);
        }else {

            map.put("rating_button",isRating);
        }
        if(isBuy==null||isBuy==0)  {
            map.put("buy_button",0);

        }else {
            map.put("buy_button",isBuy);

        }
        return  map;
    }


}
