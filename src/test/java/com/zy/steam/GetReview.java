package com.zy.steam;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GetReview {
    //接口文档
    //https://partner.steamgames.com/doc/store/getreviews
    private static final String BASE_URL = "https://store.steampowered.com/appreviews/";
    private static final String QUERY = "?json=1&num_per_page=100&review_type=all&purchase_type=all";


    public static void main(String[] args) {
        String gameId = "570"; // Dota 2
//            JSONObject jsonObject = readJsonFromUrl("https://store.steampowered.com/appreviews/2099790?json=1&num_per_page=100&review_type=all&purchase_type=all&cursor=AoIIPwBCt37uoosE");
        ArrayList<Rating> ratingLIst = scrapeReviews(gameId);
//        System.out.println("ratingList["+ ratingLIst.size() +"]:  "  +ratingLIst);

    }

    //是否获取为空
    public static Boolean whetherGetNull(Integer success, Integer num_reviews) {
        System.out.println("whetherGetNull");
        if (success.equals(1) && (!num_reviews.equals(0))) {//不为空
            return false;
        } else {//为空
            return true;
        }
    }

    //是否读完了
    public static Boolean whetherGetEnd(String oldCursor, String newCursor) {
        System.out.println("whetherGetEnd");
        return oldCursor.equals(newCursor);
    }

    public static ArrayList<com.zy.steam.GetReview.Rating> scrapeReviews(String gameId) {
        ArrayList<com.zy.steam.GetReview.Rating> ratingList = new ArrayList<>();
        String cursor = "AoIIPzTWG3iflYEE";
        int count=1;
        while (true) {
            try {

                //代理和url
                String proxyHost = "127.0.0.1";
                int proxyPort = 7890;
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                URL url = new URL(BASE_URL + gameId + QUERY + "&cursor=" + cursor);
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
                if (whetherGetNull(success, num_reviews)){
                    break;
                }
                JSONArray jsonReviews = json.getJSONArray("reviews");
                for (int i = 0; i < jsonReviews.length(); i++) {
                    JSONObject item = jsonReviews.getJSONObject(i);
                    String game_id = "570";
                    String player_id = item.getJSONObject("author").getString("steamid");
                    Integer rating = item.getBoolean("voted_up") == true ? 1 : -1;
                    String comment = item.getString("review");
                    Long create_time = item.getLong("timestamp_created");
                    Rating ratingObject = new Rating(game_id, player_id, rating, comment, create_time);
                    System.out.println("[[["+(count++)+"]]]\n");
                    System.out.println("<<<"+ratingObject+">>>\n");
                    ratingList.add(ratingObject);

                }

                String tempCursor = json.getString("cursor").replaceAll("\\+", "");
                if (whetherGetEnd(cursor, tempCursor)){
                    System.out.println(cursor);
                    System.out.println(tempCursor);
                    System.out.println(response.toString());
                    break;
                }
                cursor = tempCursor;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return ratingList;
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream(); Scanner scanner = new Scanner(is)) {
            scanner.useDelimiter("\\A");
            String jsonString = scanner.hasNext() ? scanner.next() : "";
            return new JSONObject(jsonString);
        }
    }


    private static class Rating {
        private String game_id;
        private String player_id;
        private Integer rating;
        private String comment;
        private Long create_time;

        public Rating(String game_id, String player_id, Integer rating, String comment, Long create_time) {
            this.game_id = game_id;
            this.player_id = player_id;
            this.rating = rating;
            this.comment = comment;
            this.create_time = create_time;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(String player_id) {
            this.player_id = player_id;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Long create_time) {
            this.create_time = create_time;
        }

        public String toString() {
            return game_id + "\n"
                    + player_id + "\n"
                    + rating + "\n"
                    + comment + "\n"
                    + create_time + "\n";
        }
    }

    public static class JsonArrange {
        private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
            try (InputStream is = new URL(url).openStream(); Scanner scanner = new Scanner(is)) {
                scanner.useDelimiter("\\A");
                String jsonString = scanner.hasNext() ? scanner.next() : "";
                return new JSONObject(jsonString);
            }
        }
        public static void main(String[] args) {
            try {
                JSONObject json = readJsonFromUrl("https://store.steampowered.com/appreviews/2099790?json=1&num_per_page=20&review_type=all&purchase_type=all&cursor=*");
                System.out.println(json);
                System.out.println(json.has("success"));
                System.out.println(json.getInt("success"));//1
                System.out.println(json.getString("success"));//1
    //            System.out.println(json.getJSONObject("query_summary").getString("num_reviews"));//3
                System.out.println(json.getJSONObject("query_summary").getInt("num_reviews"));//3
                System.out.println(json.getJSONArray("reviews").length());//3

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
