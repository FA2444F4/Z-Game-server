package com.zy.service;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdministratorService {
    /**
     * 根据id查找管理员信息
     * @param id
     * @return
     */
    public Administrator getAdministratorById(Integer id);

    /**
     * 根据id搜索管理员所有信息并封装在param里
     * @param id
     * @return
     */
    public Param getAdministratorInfo(Integer id);

    /**
     * 修改管理员的administrator和user
     * @param administrator
     * @param user
     * @return
     */
    public Boolean updateAdministratorInfo(Administrator administrator, User user);
}
