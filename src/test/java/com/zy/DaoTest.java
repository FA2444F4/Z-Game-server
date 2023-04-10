package com.zy;

import com.zy.dao.GameDao;
import com.zy.dao.GameRatingDao;
import com.zy.dao.TagDao;
import com.zy.domain.Game;
import com.zy.domain.User;
import com.zy.service.GameRatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class DaoTest {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private GameRatingDao game_rating;
    @Autowired
    private GameRatingService gameRatingService;

    //测试lombok
    @Test
    void user(){
        User user = new User();
        user.setId(111);
        System.out.println(user);
    }

    @Test
    void a1(){
        List<String> strings = tagDao.selectTagNameByGameId(1);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    void a2(){
        System.out.println(game_rating.countRatingAvg(1));
        System.out.println(game_rating.countRatingAvg(10));
    }

    @Test
    void a3(){
        for (Game element : gameDao.selectGameByInput("糖")) {
            System.out.println(element);
        }

    }

    @Test
    void a4(){
        System.out.println(game_rating.getGameIdAndRatingFromOnePlayer(100001));
    }

    @Test
    void a5(){
       List< Map<String ,Integer>> mapList=game_rating.getGameIdAndRatingFromOnePlayer(100001);
        System.out.println(mapList);
        System.out.println(mapList.size());
        System.out.println(mapList.get(0).get("game_id"));//1
        System.out.println(mapList.get(0).get("rating"));//4
        System.out.println(mapList.get(0).size());//2
    }

    @Test
    void a6(){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,2);
        System.out.println(map);
    }

    @Test
    void a7(){
        System.out.println(gameRatingService.getGameIdAndRatingFromOnePlayer(100001));
    }

}
