package com.zy.service.impl;

import com.zy.dao.DeveloperDao;
import com.zy.domain.Developer;
import com.zy.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    @Autowired
    private DeveloperDao developerDao;

    @Override
    public Developer getDeveloperById(Integer id) {
        return developerDao.getDeveloperById(id);
    }
}
