package com.zy.util;

import org.springframework.util.DigestUtils;

public class DataUtil {
    public static String MD5(String root){
        String pws = DigestUtils.md5DigestAsHex(root.getBytes());
        return pws;
    }

    public static Long timestamp(){
        return System.currentTimeMillis()/1000;
    }
}
