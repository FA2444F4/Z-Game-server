package com.zy;

import com.zy.dao.BookDao;
import com.zy.domain.Book;
import com.zy.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private BookDao bookDao;

    //传统mybatis
    @Test
    public void testGetById(){
        Book book = bookDao.getById(1);
        System.out.println(book);
    }


}
