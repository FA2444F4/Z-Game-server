package com.zy.util;

import org.springframework.util.DigestUtils;

public class DataUtil {
    //生成md5
    public static String MD5(String root){
        String pws = DigestUtils.md5DigestAsHex(root.getBytes());
        return pws;
    }

    //获取当前时间戳 秒级别
    public static Long timestamp(){
        return System.currentTimeMillis()/1000;
    }

    public static Integer makeNumberToInteger(Object object){
        if(object.getClass().equals(Integer.class)){
            return (Integer) object;
        }else if(object.getClass().equals(Double.class)){
            return ((Double)object).intValue();
        }else {
            System.out.println(object.getClass());
            return (Integer) object;
        }
    }
}
