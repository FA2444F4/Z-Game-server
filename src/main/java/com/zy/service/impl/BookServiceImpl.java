package com.zy.service.impl;

import com.zy.dao.BookDao;
import com.zy.domain.Book;
import com.zy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Boolean save(Book book) {
        //System.out.println(book);///
        return bookDao.save(book) > 0;

    }

    @Override
    public Boolean update(Book book) {
        //System.out.println(book);///
        return bookDao.update(book) > 0;
    }

    @Override
    public Boolean delete(Integer id) {
        return bookDao.delete(id) > 0;
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
