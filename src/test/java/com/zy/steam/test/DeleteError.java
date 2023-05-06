package com.zy.steam.test;

import com.zy.dao.*;
import com.zy.domain.GameRating;
import com.zy.service.GameRatingService;
import com.zy.service.TagService;
import com.zy.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class DeleteError {
    @Autowired
    private GameDao gameDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private Game_tagDao gameTagDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private GameRatingDao gameRatingDao;
    @Autowired
    private UserWalletDao userWalletDao;
    @Autowired
    private PlayerGameDao playerGameDao;
    @Autowired
    private GameRatingService gameRatingService;
    @Autowired
    private TagService tagService;
    @Test
    void e1(){
        List<Integer> idList = gameDao.selectGameIdWhichError();
        for (Integer id : idList) {
            gameRatingDao.deleteGameId(id);
            gameDao.deleteGameByGameId(id);
            }
    }


    //删除评论少的评论,,player,user
    @Test
    void s1(){
        List<Long> reserveGameIdList = gameDao.findUsersWithRatingCountGreaterThanEqual10();
        for (Long id : playerDao.selectPlayerIdList()) {
            if(!reserveGameIdList.contains(id)){
                //删除评论
                gameRatingDao.deleteAccount(id);
                //删除player
                playerDao.deleteAccount(id);
                //删除user
                userDao.deleteAccount(id);
            }
        }


    }

    //初始化用户钱包
    @Test
    void S2(){
        List<Long> idList = userDao.selectUserIdList();
        for (Long id : idList) {
            userWalletDao.initUserWallet(id,0.0, DataUtil.timestamp(),DataUtil.timestamp(),1);
        }
    }

    //标记游戏拥有
    @Test
    void s3(){
        for (GameRating rating : gameRatingDao.selectAll()) {
            //判断游戏是否已拥有
            Map<String, Object> map = playerGameDao.selectPlayerGameByOne(rating.getPlayer_id(), rating.getGame_id());
            if(map==null){
                //未拥有
                playerGameDao.addPlayerGame(rating.getPlayer_id(),rating.getGame_id(),DataUtil.timestamp(),1,1);
            }
        }

    }

    @Test
    void a4(){
        System.out.println(gameRatingService.getNewPointAndGame(100001L));
    }

    @Test
    void a5(){
        for (Map<String, Integer> map : gameRatingService.getGameIdAndRatingFromOnePlayer(76561197963106395L)) {

            if((int)map.get("rating")>=1){
                System.out.println("ok");
            }
        }

        System.out.println("-----------------------");
        for (Map<String, Integer> map : gameRatingDao.getGameIdAndRatingFromOnePlayer(76561197963106395L)) {

            if((int)map.get("rating")>=1){
                System.out.println("ok");
            }
        }
    }


    @Test
    void a6(){
        System.out.println(tagService.selectTagIdListFromGame(620));
    }



}
