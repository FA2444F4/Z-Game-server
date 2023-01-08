package com.zy;

import com.zy.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DaoTest {
    //测试lombok
    @Test
    void user(){
        User user = new User();
        user.setId(111);
        System.out.println(user);
    }
}
