package com.zy.service;

import com.zy.domain.Developer;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeveloperService {
    /**
     * 根据id查找开发商信息
     * @param id
     * @return
     */
    public Developer getDeveloperById(Long id);

    /**
     * 修改开发商的developer和user
     * @param user
     * @param developer
     * @return
     */
    public Boolean updateDeveloperInfo(User user, Developer developer);

    /**
     * 根据id搜索开发商所有信息并封装在param里
     * @param id
     * @return
     */
    public Param getDeveloperInfo(Long id);
}
