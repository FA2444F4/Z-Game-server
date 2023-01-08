package com.zy;

import com.zy.dao.UserDao;
import com.zy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;
    @Test
    void verifyUser(){
        /*System.out.println(userService.verifyUserByUsernameAndPassword("admin","123456"));
        System.out.println(userService.verifyUserByUsernameAndPassword("player","123456"));
        System.out.println(userService.verifyUserByUsernameAndPassword("developer","123456"));
        System.out.println(userService.verifyUserByUsernameAndPassword("XXX","123456"));*/
    }

    @Test
    void a0(){
        System.out.println(userService.verifyUsernameIsUniQue("admin"));
        System.out.println(userService.verifyUsernameIsUniQue("admin1"));
    }
}
