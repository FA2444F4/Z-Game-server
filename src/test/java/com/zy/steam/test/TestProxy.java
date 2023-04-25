package com.zy.steam.test;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Scanner;
public class TestProxy {
    public static void main(String[] args) throws Exception {
//        String urlString = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
        String urlString = "https://store.steampowered.com/appreviews/570?json=1&num_per_page=100&review_type=all&purchase_type=all&cursor=*&key=A9A2CE06F5FE5FBBB55FBA287463C2AF";

        // 设置代理服务器地址和端口号
        String proxyHost = "127.0.0.1";
        int proxyPort = 7890;
        // 创建代理对象
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        // 创建 URL 对象
        URL url = new URL(urlString);
        // 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        // 设置请求方式为 GET
        conn.setRequestMethod("GET");
        // 发送请求
        conn.connect();
        // 读取响应内容
        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        // 输出响应内容
        System.out.println(response.toString());
        // 断开连接
        conn.disconnect();
    }
}
