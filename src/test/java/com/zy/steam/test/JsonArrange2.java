package com.zy.steam.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class JsonArrange2 {

    public static void main(String[] args) {
        try {
            //创建URL对象
            URL url = new URL("https://store.steampowered.com/appreviews/570?json=1&num_per_page=100&review_type=all&purchase_type=all&cursor=*&key=A9A2CE06F5FE5FBBB55FBA287463C2AF");
            //打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方法为GET
            connection.setRequestMethod("GET");
            //设置请求头为接受json数据
            connection.setRequestProperty("Accept", "application/json");
            //获取响应码
            int responseCode = connection.getResponseCode();
            //如果响应码为200，表示成功
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //创建缓冲读取器读取响应输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //定义一个字符串来存储读取的一行数据
                String line = null;
                //创建一个字符串缓冲区来存储所有读取的数据
                StringBuffer buffer = new StringBuffer();
                //按行读取数据并追加到字符串缓冲区
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                //关闭读取器
                reader.close();
                //把字符串缓冲区转换为字符串
                String result = buffer.toString();
                //输出结果
                System.out.println(result);
                JSONObject json= new JSONObject(result);
                System.out.println(json.getInt("success"));
            } else {
                //如果响应码不为200，表示失败
                System.out.println("GET request failed.");
            }
        } catch (Exception e) {
            //处理异常
            e.printStackTrace();
        }
    }
}
