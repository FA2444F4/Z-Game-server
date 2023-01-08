package com.zy.service.impl;

import com.zy.dao.AdministratorDao;
import com.zy.domain.Administrator;
import com.zy.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorDao administratorDao;

    @Override
    public Administrator getAdministratorById(Integer id) {
        return administratorDao.getAdministratorById(id);
    }
}
