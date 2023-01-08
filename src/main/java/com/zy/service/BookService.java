package com.zy.service;

import com.zy.domain.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookService {
    /**
     * 保存
     * @param book
     * @return
     */
    public Boolean save(Book book);

    /**
     * 修改
     * @param book
     * @return
     */
    public Boolean update(Book book);

    /**
     * 按id删除
     * @param id
     * @return
     */
    public Boolean delete(Integer id);

    /**
     * 按id查询
     * @param id
     * @return
     */
    public Book getById(Integer id);

    /**
     * 查询全部
     * @return
     */
    public List<Book> getAll();
}
