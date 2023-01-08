package com.zy;

import com.zy.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlayerTest {
    @Autowired
    private PlayerService playerService;
    @Test
    void a(){
        System.out.println(playerService.getPlayerById(100001));
    }
}
