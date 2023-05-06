package com.zy.steam.test;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.util.Map;

@SpringBootTest
public class CsvToJson {
    public static void main(String[] args) throws IOException {
        // 读取CSV文件
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
        File csvFile = new File("C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews.csv");
        Object[] csvData = csvMapper.readerFor(Map.class).with(csvSchema).readValues(csvFile).readAll().toArray();

        // 将CSV数据转换为JSON格式
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("C:\\Code\\Bistu\\GraduationProject\\8G_reviews\\steam_reviews.json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, csvData);
    }
}
