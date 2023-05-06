package com.zy.steam.test;

import com.opencsv.*;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReaderBuilder;
import com.zy.dao.*;
import com.zy.domain.Game;
import com.zy.domain.GameRating;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
@SpringBootTest
public class CscReader {
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

    //根据是否点赞生成随机数
    public static int getVote(Boolean voted_up) {
        if (voted_up) {
            return (int) (Math.random() * 3) + 3; // 生成3到5的随机整数
        } else {
            return (int) (Math.random() * 3) + 1; // 生成1到3的随机整数
        }
    }

    //手机号
    public static String generateRandomPhoneNumber() {
        String[] prefix = {"133", "149", "153", "173", "177", "180", "181", "189", "199",
                "130", "131", "132", "145", "155", "156", "166", "175", "176", "185", "186",
                "134", "135", "136", "137", "138", "139", "147", "148", "150", "151",
                "152", "157", "158", "159", "172", "178", "182", "183", "184", "187",
                "188", "198",
                "170", "171"};
        String phoneNumber = prefix[(int) (Math.random() * prefix.length)];
        for (int i = 0; i < 8; i++) {
            phoneNumber += (int) (Math.random() * 10);
        }
        return phoneNumber;
    }
    @Test
    public void makeUserPlayerRating() throws IOException {
        String csvFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews_delete_odd.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] nextLine;
//        int count=0;
        int userNum=1;
        while (true) {
//            if((count++)>=100)
//                break;
            try {
                if (!((nextLine = reader.readNext()) != null)) break;
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
            // 过滤异常行
            if (nextLine.length!=23){
                continue;
            }
            String language = nextLine[4];
            //筛选简中
            if (language.equals("schinese")) {
                //添加游戏//冗余
                Integer app_id=Integer.valueOf(nextLine[1]);
                if (gameDao.selectGameById(app_id) == null) {
                    //如果游戏不存在
                    Game game = new Game(app_id, null, 0.0, "error", null, null, null, null, null, 0, DataUtil.timestamp());
                    gameDao.addGame2(game);
                }
                //添加user
                //添加player
                Long steamid=Long.valueOf(nextLine[16]);
                if(userDao.getUserById(steamid)==null){//如果是新用户
                    System.out.println("新增第 "+(userNum++)+" 个新用户: " +steamid);
                    User user = new User(steamid,steamid.toString(),"e10adc3949ba59abbe56e057f20f883e",1, DataUtil.timestamp());
                    userDao.AddUserSteam(user);
                    Player player = new Player(steamid, 0, 0.0, steamid.toString(), steamid.toString(), (int) (Math.random() * 2), generateRandomPhoneNumber());
                    playerDao.insertPlayer(player);
                }
                //添加rating
                if(gameRatingDao.selectRatingByPlayerIdAndGameId(steamid,app_id)==null){//新评论
                    System.out.println("新增新评论   游戏id: "+app_id);
                    GameRating gameRating = new GameRating(app_id, steamid, getVote(Boolean.valueOf(nextLine[8])), nextLine[5], Long.valueOf(nextLine[6]), 1);
                    gameRatingDao.insertGameRating(gameRating);
                }
            }
        }
        /*System.out.println("----------------输出app_id-------------------------");
        for (Integer app_id : set) {
            System.out.print(","+app_id);
        }*/
    }
    /*public static void main(String[] args) throws IOException {
        //预处理
        *//*String inputCsvFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews.csv";
        String outputCsvFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews_ok.csv";
        BufferedReader br = new BufferedReader(new FileReader(inputCsvFile));
        FileWriter fw = new FileWriter(outputCsvFile);
        String line;
        while ((line = br.readLine()) != null) {
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '\"') {
                    count++;
                }
                if (count % 2 == 0 && c == ',') {
                    sb.append('|');
                } else {
                    sb.append(c);
                }
            }
            fw.write(sb.toString() + "\n");
        }
        br.close();
        fw.close();
        ////////////*//*
        *//*String outputCsvFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews.csv";
        CSVReader reader = new CSVReader(new FileReader(outputCsvFile));
        String[] nextLine;
        HashSet<Integer> set = new HashSet<>();
//        int count=0;
        while (true) {
//            if((count++)>=100)
//                break;
            try {
                if (!((nextLine = reader.readNext()) != null)) break;
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }

            // 过滤异常行
            if (nextLine.length!=23){
                continue;
            }
            String language = nextLine[4];
            //筛选简中
            if (language.equals("schinese")) {
                //判断游戏是否出现过
                Integer app_id = Integer.valueOf(nextLine[1]);
                //如果不包含app_id
                if (!set.contains(app_id)) {
                    System.out.println("add:   "+app_id);
                    set.add(app_id);
                }
            }
        }
        System.out.println("----------------输出app_id-------------------------");
        for (Integer app_id : set) {
            System.out.print(","+app_id);
        }*//*
    }*/


}
/*
游戏
标签
user
player
rating
 */
