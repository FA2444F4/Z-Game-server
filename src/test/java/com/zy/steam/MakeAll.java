package com.zy.steam;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MakeAll {
    public static void main(String[] args) throws IOException {
        String csvFile = "C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        String[] nextLine;
        int count=0;
        while (true) {
            if ((count++)>=10)
                break;
            try {
                if (!((nextLine = reader.readNext()) != null)) break;
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
            // 处理每一行数据
            for (int i = 0; i < nextLine.length; i++) {
                String language=nextLine[3];
                //只筛选简中的
                if (language.equals("schinese")){
                    //判断游戏是否出现过

                }
            }

        }
    }
}
