package com.zy.service;

import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdministratorService {
    //根据id查找管理员信息

    public Administrator getAdministratorById(Long id);

    //根据id搜索管理员所有信息并封装在param里

    public Param getAdministratorInfo(Long id);


    //修改管理员的administrator和user

    public Boolean updateAdministratorInfo(Administrator administrator, User user);
}
