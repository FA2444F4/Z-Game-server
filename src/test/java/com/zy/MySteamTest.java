package com.zy;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MySteamTest {
    private static final String BASE_URL = "https://store.steampowered.com/appreviews/";
    private static final String QUERY = "?json=1&num_per_page=100&review_type=all&purchase_type=all";

    public static void main(String[] args) {
        String gameId = "570"; // Dota 2
        ArrayList<SteamReview> reviews = scrapeReviews(gameId);
        for (SteamReview review : reviews) {
            System.out.println(review);
        }
    }

    public static ArrayList<SteamReview> scrapeReviews(String gameId) {
        ArrayList<SteamReview> reviews = new ArrayList<>();
        int currentPage = 1;
        int totalPages = 2;
        while (currentPage < totalPages) {
            try {
                String url = BASE_URL + gameId + QUERY + "&cursor=" + currentPage;
                JSONObject json = readJsonFromUrl(url);
                System.out.println(json);
                /*JSONArray jsonReviews = json.getJSONArray("reviews");
                for (int i = 0; i < jsonReviews.length(); i++) {
                    JSONObject jsonReview = jsonReviews.getJSONObject(i);
                    String id = jsonReview.getString("author");
                    String content = jsonReview.getString("review");
                    int rating = jsonReview.getInt("recommendationid");
                    SteamReview review = new SteamReview(id, content, rating);
                    reviews.add(review);
                }
                totalPages = json.getJSONObject("query_summary").getInt("total_pages");
                currentPage++;*/
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream(); Scanner scanner = new Scanner(is)) {
            scanner.useDelimiter("\\A");
            String jsonString = scanner.hasNext() ? scanner.next() : "";
            return new JSONObject(jsonString);
        }
    }

    private static class SteamReview {
        private String id;
        private String content;
        private int rating;

        public SteamReview(String id, String content, int rating) {
            this.id = id;
            this.content = content;
            this.rating = rating;
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public int getRating() {
            return rating;
        }

        public String toString() {
            return "ID: " + id + "\nContent: " + content + "\nRating: " + rating;
        }
    }
}