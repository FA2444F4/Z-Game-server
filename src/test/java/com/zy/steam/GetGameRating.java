package com.zy.steam;

import com.zy.dao.*;
import com.zy.domain.GameRating;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.util.DataUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
public class GetGameRating {

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

    private static final Logger logger = LogManager.getLogger(GetGameRating.class);

    //是否获取为空
    public static Boolean whetherGetNull(Integer success, Integer num_reviews) {
        if (success.equals(1) && (!num_reviews.equals(0))) {//不为空
            return false;
        } else {//为空
            logger.error("获取为空  ,success=" + success + ",num_reviews=" + num_reviews);

            return true;
        }
    }

    //是否读完了
    public static Boolean whetherGetEnd(String oldCursor, String newCursor) {
        return oldCursor.equals(newCursor)||newCursor.equals("")||(newCursor==null);
    }

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
    public void deleteAll() {
        gameRatingDao.deleteAll();
        playerDao.deleteAll();
        userDao.deleteAll();
    }

    @Test
    public void getGameRating() {
        //https://store.steampowered.com/api/appdetails?appids=570&cc=CN&l=zh
        String BASE_URL = "https://store.steampowered.com/appreviews/";
        //570
//        String QUERY = "?json=1&cc=CN&l=zh&num_per_page=100&review_type=all&purchase_type=all&cursor=";
        String QUERY = "?json=1&cursor=";
        //cursor

        int appNum = 1;
        List<Integer> appidList = gameDao.selectGameIdList();
//        appidList.remove(0);
//        appidList.remove(1);
//        appidList.remove(2);
//        appidList.remove(3);
        for (int appid : appidList) {


            logger.error("执行到第 " + (appNum) + " 个游戏.\n");
            Integer batch = 1;
            Integer count = 1;
            String cursor = "*";
            Integer total_reviews = 0;
            while (true) {//cursor重复才结束
                //暂停一会
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.error("第 " + (appNum) + " 个游戏的第 " + (batch) + " 批次请求");
                try {
                    //代理和url
                    String proxyHost = "127.0.0.1";
                    int proxyPort = 7890;
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                    URL url = new URL(BASE_URL + appid + QUERY + URLEncoder.encode(cursor, "UTF-8"));
                    logger.error("url:   " + url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    Scanner scanner = new Scanner(conn.getInputStream());
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();
                    conn.disconnect();
                    //解析json
                    JSONObject json = new JSONObject(response.toString());
                    Integer success = json.getInt("success");
                    Integer num_reviews = json.getJSONObject("query_summary").getInt("num_reviews");
                    System.out.println("success: "+success+"    ,num_reviews: "+num_reviews);
                    if (batch == 1) {
                        total_reviews = json.getJSONObject("query_summary").getInt("total_reviews");
                    }
                    if (whetherGetNull(success, num_reviews)) {
                        logger.error("whetherGetNull");
                        continue;
                    }
                    JSONArray reviews = json.getJSONArray("reviews");
                    for (int i = 0; i < reviews.length(); i++) {
                        logger.error("执行到第 " + (appNum) + " 个游戏的第 " + (count++) + "/" + (total_reviews) + " 条评论");
                        JSONObject reviewItem = reviews.getJSONObject(i);
                        Long steamid = Long.valueOf(reviewItem.getJSONObject("author").getString("steamid"));
                        String review = reviewItem.getString("review");
                        Long timestamp_created = reviewItem.getLong("timestamp_created");
                        Boolean voted_up = reviewItem.getBoolean("voted_up");
                        Integer rating = getVote(voted_up);
                        //user,player//////////////////////////////////////
                        //如果user不存在就创造user和player
                        //判断user是否存在
                        User tempUser = userDao.getUserById(steamid);
                        if (tempUser == null) {//如果是新用户
                            //创建user
                            String username = steamid.toString();
                            String password = "e10adc3949ba59abbe56e057f20f883e";
                            Integer type = 1;
                            Long create_time = DataUtil.timestamp();
                            User user = new User(steamid, username, password, type, create_time);
                            userDao.AddUserSteam(user);
                            //创建player
                            Integer status = 0;
                            Double deposit = 0.0;
                            String nick_name = username;
                            String name = username;
                            Integer sex = (int) (Math.random() * 2);
                            String phone = generateRandomPhoneNumber();
                            Player player = new Player(steamid, status, deposit, nick_name, name, sex, phone);
                            playerDao.insertPlayer(player);
                        }
                        //rating
                        //如果rating没存在过
                        if (gameRatingDao.whetherRatingExist(steamid, appid) .equals(0)) {
                            GameRating gameRating = new GameRating(appid, steamid, rating, review, timestamp_created, 1);
                            gameRatingDao.insertGameRating(gameRating);
                        }else {
                            System.out.println("已存在同用户同游戏的评论");
                        }

                    }
//                    String tempCursor = json.getString("cursor").replaceAll("\\+", "");
                    String tempCursor = json.getString("cursor").replace("+", "%2B");
                    if (whetherGetEnd(cursor, tempCursor)) {
                        logger.error("whetherGetEnd   ,cursor=" + cursor);
                        break;
                    }
                    cursor = tempCursor;
                    batch++;


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            appNum++;
        }
    }

}
