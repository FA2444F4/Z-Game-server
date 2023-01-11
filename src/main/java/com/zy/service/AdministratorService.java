package com.zy.service;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
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
}
