package com.zy;

import com.zy.dao.*;
import com.zy.util.SqlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
@SpringBootTest
public class CreateSQL {
    @Autowired
    private GameDao gameDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlayerGameDao playerGameDao;
    @Autowired
    private UserWalletDao userWalletDao;
    @Autowired
    private GameRatingDao gameRatingDao;
    @Autowired
    private EssayDao essayDao;

    //获取数据的方法
   public HashMap<String,Object> getPreloadData(){
        //playerIdList
        //gameIdList
        HashMap<String, Object> map = new HashMap<>();
        List<Integer> gameIdList = gameDao.selectGameIdList();
        List<Integer> playerIdList = playerDao.selectPlayerIdList();
        map.put("gameIdList",gameIdList);
        map.put("playerIdList",playerIdList);
        return map;
    }

    int[] player_id_list={100001,100004,100005,100006,100007,100008,100009,100010,100011,100014,100016};
    @Test
    public void a1(){//随机创建玩家评价
        int[] player_id_list={100001,100004,100005,100006,100007,100008,100009,100010,100011,100014,100016};
        int[] game_id_list={1,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};
        for (int i : player_id_list) {
            for (int j : game_id_list) {
                String sql = new String("insert into game_rating values (");
                sql+=j+",";
                sql+=i+",";
                int random = (new Random().nextInt(5))+1;
                sql+=random+",";
                sql+="'无评论',";
                sql+="1679311800,";
                sql+="1);";
                System.out.println(sql);
            }
        }
    }
    @Test
    public void makeRandom(){
        for (int i=0;i<10;i++){
            //1~5
            int random = (new Random().nextInt(5))+1;
            System.out.println(random);
        }


    }

    @Test
    public void a2(){//初始化钱包
        //
        Double balance=500.0;
        Long create_time=1673009300L;
        Integer wallet_status=1;
        for (int id=100000;id<=100017;id++){
            String sql = new String("insert into user_wallet values (");
            sql+=id+",";
            sql+=balance+",";
            sql+=create_time+",";
            sql+=create_time+",";
            sql+=wallet_status+");";
            System.out.println(sql);
        }
    }

    @Test
    public void a3(){//初始化player_game

        HashMap<String, Object> map = getPreloadData();
        List<Integer> playerIdList=(List<Integer>)map.get("playerIdList");
        List<Integer> gameIdList=(List<Integer>)map.get("gameIdList");
        for (Integer playerId : playerIdList) {
            for (Integer gameId : gameIdList) {
                String sql = new String("insert into player_game values (");
                sql+=playerId+",";
                sql+=gameId+",";
                sql+=1679311800L+",";
                sql+=1+",";
                sql+=1;
                sql+=");";
                System.out.println(sql);
            }
        }




    }


    //sss 初始化评论
    @Test
    public void initRating() {
        List<Integer> playerIdList=playerDao.selectPlayerIdList();
        List<Integer> gameIdList=gameDao.selectGameIdList();
        for (int i : playerIdList) {
            for (int j : gameIdList) {
                String sql = new String("insert into game_rating values (");
                sql+=j+",";
                sql+=i+",";
//                int random = (new Random().nextInt(5))+1;
                int random = 0;
                sql+=random+",";
                sql+="'无评论',";
                sql+="1679311800,";
                sql+="1);";
                System.out.println(sql);
            }
        }
    }

    //sss 删除测试用户
    @Test
    public void deleteTestUser(){
        String username="test";
        Integer user_id = userDao.getIdByUsername(username);
        //删除钱包


    }







}
