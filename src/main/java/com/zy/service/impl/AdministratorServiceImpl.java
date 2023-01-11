package com.zy.service.impl;

import com.zy.dao.AdministratorDao;
import com.zy.dao.UserDao;
import com.zy.domain.Administrator;
import com.zy.domain.Param;
import com.zy.domain.User;
import com.zy.service.AdministratorService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorDao administratorDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Administrator getAdministratorById(Integer id) {
        return administratorDao.getAdministratorById(id);
    }

    @Override
    public Param getAdministratorInfo(Integer id) {
        User user = userDao.getUserById(id);
        Administrator administrator = administratorDao.getAdministratorById(id);
        Param param = new Param();
        param.setUser(user);
        param.setAdministrator(administrator);
        return param;
    }

    @Override
    public Boolean updateAdministratorInfo(Administrator administrator, User user) {
        user.setPassword(DataUtil.MD5(user.getPassword()));
        administratorDao.updateAdministratorInfo(administrator);
        userDao.updateUserInfo(user);

        return true;
    }
}
