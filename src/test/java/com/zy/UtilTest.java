package com.zy;

import com.zy.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilTest {
    //测试md5
    @Test
    public void md5(){
        System.out.println(DataUtil.MD5("123456"));
        //e10adc3949ba59abbe56e057f20f883e
    }

    //时间戳
    @Test
    public void time(){
        Long time= System.currentTimeMillis();
        System.out.println(time);
    }
}
