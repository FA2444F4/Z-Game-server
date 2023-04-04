package com.zy;

import com.zy.dao.GameDao;
import com.zy.dao.GameRatingDao;
import com.zy.dao.TagDao;
import com.zy.domain.Game;
import com.zy.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DaoTest {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private GameRatingDao game_rating;

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


}
