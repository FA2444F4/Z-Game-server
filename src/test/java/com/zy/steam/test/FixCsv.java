package com.zy.steam.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FixCsv {
    public static void main(String[] args) {
        String csvFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews.csv";
        String outputFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews_delete_odd.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             FileWriter fw = new FileWriter(outputFile)) {
            while ((line = br.readLine()) != null) {
                int quoteCount = line.length() - line.replace("\"", "").length();
                if (quoteCount % 2 == 0) {
                    fw.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
