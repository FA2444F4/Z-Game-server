package com.zy.steam;

import com.zy.dao.GameDao;
import com.zy.dao.Game_tagDao;
import com.zy.dao.TagDao;
import com.zy.domain.Tag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
public class GetGameTag {

    @Autowired
    private GameDao gameDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private Game_tagDao gameTagDao;


    /*static int[] appidList = {730
            , 578080
            , 570
            , 271590
            , 359550
            , 440
            , 105600
            , 4000
            , 252490
            , 292030
            , 945360
            , 431960
            , 230410
            , 1091500
            , 1085660
            , 550
            , 304930
            , 1245620
            , 346110
            , 381210
            , 413150
            , 227300
            , 739630
            , 218620
            , 242760
            , 236390
            , 1174180
            , 892970
            , 444090
            , 1938090
            , 291550
            , 221100
            , 620
            , 239140
            , 322330
            , 367520
            , 1172620
            , 582010
            , 374320
            , 648800
            , 1063730
            , 960090
            , 322170
            , 433850
            , 250900
            , 1145360
            , 264710
            , 275850
            , 238960
            , 377160
            , 438100
            , 1599340
            , 698780
            , 976730
            , 289070
            , 1468810
            , 49520
            , 1794680
            , 251570
            , 261550
            , 391540
            , 255710
            , 319630
            , 1118200
            , 394360
            , 814380
            , 1293830
            , 107410
            , 1240440
            , 700330
            , 632360
            , 548430
            , 284160
            , 782330
            , 1782210
            , 227940
            , 1625450
            , 22380
            , 477160
            , 435150
            , 203160
            , 10
            , 1237970
            , 108600
            , 1517290
            , 294100
            , 427520
            , 1238810
            , 444200
            , 489830
            , 220
            , 552520
            , 594650
            , 812140
            , 301520
            , 48700
            , 552990
            , 379720
            , 268910
            , 8930
            , 1203220
            , 1832640
            , 1289310
            , 646570
            , 677620
            , 306130
            , 588650
            , 386360
            , 526870
            , 262060
            , 813780
            , 400
            , 1426210
            , 12210
            , 1332010
            , 281990
            , 755790
            , 240
            , 534380
            , 1172380
            , 1551360
            , 270880
            , 8870
            , 508440
            , 1506830
            , 393380
            , 1818750
            , 1238840
            , 311210
            , 1057090
            , 433340
            , 3590
            , 220200
            , 457140
            , 1046930
            , 304050
            , 361420
            , 1222670
            , 225540
            , 629760
            , 397540
            , 222880
            , 466240
            , 362890
            , 387990
            , 674940
            , 990080
            , 219740
            , 305620
            , 714010
            , 211820
            , 581320
            , 883710
            , 291480
            , 220240
            , 594570
            , 550650
            , 1092790
            , 221380
            , 1144200
            , 244850
            , 582160
            , 236850
            , 379430
            , 823130
            , 232090
            , 204360
            , 412020
            , 323190
            , 1217060
            , 1677740
            , 552500
            , 774171
            , 223470
            , 513710
            , 238320
            , 218230
            , 1151640
            , 848450
            , 203770
            , 70
            , 219990
            , 546560
            , 224260
            , 219150
            , 1811260
            , 632470
            , 244210
            , 359320
            , 1593500
            , 397900
            , 1222140
            , 331470
            , 613100
            , 1222680
            , 311690
            , 20920
            , 1366540
            , 282070
            , 208650
            , 460930
            , 620980
            , 504230
            , 601150
            , 268500
            , 1167630
            , 438740
            , 440900
            , 286690
            , 489520
            , 20900
            , 231430
            , 1196590
            , 349040
            , 686810
            , 504370
            , 363970
            , 39210
            , 424370
            , 333930
            , 335300
            , 1158310
            , 588430
            , 787860
            , 206420
            , 1449850
            , 376210
            , 761890
            , 389730
            , 447040
            , 383870
            , 233860
            , 813820
            , 703080
            , 1361210
            , 1407200
            , 205100
            , 636480
            , 287700
            , 346900
            , 646910
            , 976310
            , 635260
            , 1113000
            , 386180
            , 683320
            , 779340
            , 212680
            , 1604030
            , 233450
            , 211420
            , 1086940
            , 239030
            , 240720
            , 238460
            , 206440
            , 750920
            , 387290
            , 674020
            , 1665460
            , 570940
            , 289650
            , 471710
            , 460950
            , 287390
            , 1721470
            , 265930
            , 493340
            , 1229490
            , 1150690
            , 760160
            , 235460
            , 516750
            , 414340
            , 881100
            , 629520
            , 55230
            , 977950
            , 582660
            , 1142710
            , 871720
            , 638970
            , 219640
            , 1404210
            , 838350
            , 418370
            , 307690
            , 1049590
            , 648350
            , 223710
            , 254700
            , 680420
            , 752590
            , 1274570
            , 200210
            , 2050650
            , 1250410
            , 1446780
            , 214950
            , 234140
            , 1151340
            , 261570
            , 113200
            , 952060
            , 678950
            , 6060
            , 380600
            , 365670
            , 274190
            , 246620
            , 747660
            , 1817070
            , 753640
            , 466560
            , 645630
            , 298110
            , 273110
            , 815370
            , 346010
            , 274170
            , 962130
            , 1313140
            , 312660
            , 1568590
            , 1030840
            , 601510
            , 868270
            , 633230
            , 253710
            , 8190
            , 1454400
            , 304430
            , 1237950
            , 1418630
            , 33230
            , 500
            , 221910
            , 884660
            , 621060
            , 644930
            , 285900
            , 431240
            , 629730
            , 10090
            , 617290
            , 409710
            , 994280
            , 1259420
            , 253230
            , 204100
            , 207610
            , 214490
            , 611500
            , 313120
            , 113400
            , 418460
            , 1250
            , 495420
            , 834910
            , 200260
            , 1147560
            , 109600
            , 239820
            , 1139900
            , 201810
            , 1466860
            , 843380
            , 844870
            , 678960
            , 200510
            , 414700
            , 424840
            , 265630
            , 242920
            , 286160
            , 880940
            , 637650
            , 470220
            , 924970
            , 35140
            , 728880
            , 1089980
            , 555570
            , 493520
            , 555160
            , 225080
            , 403640
            , 319510
            , 22330
            , 554620
            , 1328670
            , 1248130
            , 823500
            , 24960
            , 420530
            , 307780
            , 22370
            , 417860
            , 573090
            , 666140
            , 334230
            , 1178830
            , 364360
            , 200710
            , 282140
            , 1128810
            , 1326470
            , 8500
            , 851850
            , 745920
            , 1254120
            , 602960
            , 221040
            , 1290000
            , 454650
            , 438040
            , 1644960
            , 50130
            , 244450
            , 955050
            , 603850
            , 766570
            , 920210
            , 1282730
            , 1288310
            , 34330
            , 501300
            , 552100
            , 933110
            , 480490
            , 302510
            , 657200
            , 209000
            , 242860
            , 108710
            , 1189630
            , 1222700
            , 236430
            , 751780
            , 967050
            , 447530
            , 444640
            , 337000
            , 870780
            , 10180
            , 905370
            , 225840
            , 418240
            , 223750
            , 269210
            , 204300
            , 302830
            , 105450
            , 261640
            , 326460
            , 360430
            , 107100
            , 4500
            , 420
            , 505460
            , 237930
            , 1134570
            , 47890
            , 32470
            , 1419170
            , 544810
            , 972660
            , 1080110
            , 1483870
            , 502500
            , 824270
            , 1942280
            , 383120
            , 310950
            , 50300
            , 895400
            , 266840
            , 1384160
            , 1987080
            , 583950
            , 386940
            , 228380
            , 1213210
            , 48000
            , 1296830
            , 2087030
            , 599140
            , 1129580
            , 1325200
            , 942970
            , 1149460
            , 250320
            , 1714040
            , 612880
            , 367500
            , 371660
            , 690790
            , 692850
            , 335240
            , 668630
            , 393420};*/

    @Test
    public void getGame() {
        //https://store.steampowered.com/api/appdetails?appids=570&cc=CN&l=zh
        String BASE_URL = "https://store.steampowered.com/api/appdetails?cc=CN&l=zh&appids=";
        int appNum = 1;
        List<Integer> appidList = gameDao.selectGameIdList();
        for (int appid : appidList) {
            try {
                Thread.sleep(5000); // 5000毫秒 = 5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("执行到第 " + (appNum++) + " 个游戏.\n");
            try {
                //代理和url
                String proxyHost = "127.0.0.1";
                int proxyPort = 7890;
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                URL url = new URL(BASE_URL + appid);
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
                //游戏标签
                JSONObject appidObject = json.getJSONObject(String.valueOf(appid));
                JSONObject data = appidObject.getJSONObject("data");
                JSONArray genres = data.getJSONArray("genres");
                for (int i = 0; i < genres.length(); i++) {
                    JSONObject genre = genres.getJSONObject(i);

                    Integer id = Integer.valueOf(genre.getString("id"));
                    String name = genre.getString("description");
                    String description = "";
                    Tag tag = new Tag(id, name, description);
                    //判断标签是否存在
                    Integer count = tagDao.ifTagExist(id);
                    if (count.equals(0)) {//如果标签不存在
                        tagDao.addTagWhichHaveId(tag);
                        System.out.println(tag);
                    }

                }
                //游戏
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Test
    public void addGameTag() {
        //https://store.steampowered.com/api/appdetails?appids=570&cc=CN&l=zh
        String BASE_URL = "https://store.steampowered.com/api/appdetails?cc=CN&l=zh&appids=";
        int appNum = 1;
        List<Integer> appidList = gameDao.selectGameIdList();
        for (int appid : appidList) {
            //暂停5秒
            try {
                Thread.sleep(5000); // 5000毫秒 = 5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("执行到第 " + (appNum++) + " 个游戏.\n");
            try {
                //代理和url
                String proxyHost = "127.0.0.1";
                int proxyPort = 7890;
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                URL url = new URL(BASE_URL + appid);
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
                //游戏标签
                JSONObject appidObject = json.getJSONObject(String.valueOf(appid));
                JSONObject data = appidObject.getJSONObject("data");
                JSONArray genres = data.getJSONArray("genres");
                for (int i = 0; i < genres.length(); i++) {
                    JSONObject genre = genres.getJSONObject(i);

                    Integer id = Integer.valueOf(genre.getString("id"));
                    System.out.println("appid: " + appid + "    ,tagId:    " + id);
                    gameTagDao.addGame_tag(appid, id);

                }
                //游戏
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
