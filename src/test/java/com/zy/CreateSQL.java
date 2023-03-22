package com.zy;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class CreateSQL {
    @Test
    public void a1(){
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
}
