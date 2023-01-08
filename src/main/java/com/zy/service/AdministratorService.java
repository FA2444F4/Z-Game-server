package com.zy.service;

import com.zy.domain.Administrator;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdministratorService {
    /**
     * 根据id查找管理员信息
     * @param id
     * @return
     */
    public Administrator getAdministratorById(Integer id);
}
