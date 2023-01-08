package com.zy.service;

import com.zy.domain.Developer;
import com.zy.domain.Player;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeveloperService {
    /**
     * 根据id查找开发商信息
     * @param id
     * @return
     */
    public Developer getDeveloperById(Integer id);
}
