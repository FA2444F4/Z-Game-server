package com.zy;

import com.zy.dao.AdministratorDao;
import com.zy.service.AdministratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdministratorTest {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private AdministratorDao administratorDao;

    @Test
    void a(){
        System.out.println(administratorService.getAdministratorById(100000));

    }
}
