package com.zy.service.impl;

import com.zy.dao.TagDao;
import com.zy.domain.Tag;
import com.zy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    @Override
    public Boolean addTag(Tag tag) {
        Integer count= tagDao.addTag(tag);
        return !(count==0);
    }

    @Override
    public Boolean ifTagNoExist(String name) {
        Tag tag = tagDao.selectTagByName(name);
        return tag==null;
        //搜不出来说明不存在,就可以
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public Boolean deleteTag(Integer id) {
        Integer count=tagDao.deleteTag(id);
        return count>0;
    }

    @Override
    public List<Tag> selectTagByNameLike(String name) {
        return tagDao.selectTagByNameLike(name);
    }

    @Override
    public Boolean updateTag(Tag tag) {
        Integer count=tagDao.updateTag(tag);
        return count>0;
    }
}
