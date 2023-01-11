package com.zy.service;

import com.zy.domain.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TagService {
    /**
     * 新增标签
     * @param tag
     * @return
     */
    public Boolean addTag(Tag tag);

    /**
     * 判断改标签是否已经存在
     * @param name
     * @return
     */
    public Boolean ifTagNoExist(String name);

    /**
     * 获取所有标签
     * @return
     */
    public List<Tag> getAllTag();

    /**
     * 根据id删除标签
     * @param id
     * @return
     */
    public Boolean deleteTag(Integer id);

    /**
     * 根据名称模糊查询tag
     * @param name
     * @return
     */
    public List<Tag> selectTagByNameLike(String name);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    public Boolean updateTag(Tag tag);
}
