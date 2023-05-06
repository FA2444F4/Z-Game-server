package com.zy.service.impl;

import com.zy.dao.DeveloperDao;
import com.zy.dao.UserDao;
import com.zy.domain.Developer;
import com.zy.domain.Param;
import com.zy.domain.Player;
import com.zy.domain.User;
import com.zy.service.DeveloperService;
import com.zy.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    @Autowired
    private DeveloperDao developerDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Developer getDeveloperById(Long id) {
        return developerDao.getDeveloperById(id);
    }

    @Override
    public Boolean updateDeveloperInfo(User user, Developer developer) {
        user.setPassword(DataUtil.MD5(user.getPassword()));
        userDao.updateUserInfo(user);
        developerDao.updateDeveloperInfo(developer);
        return true;
    }

    @Override
    public Param getDeveloperInfo(Long id) {
        User user = userDao.getUserById(id);
        Developer developer = developerDao.getDeveloperById(id);
        Param param = new Param();
        param.setUser(user);
        param.setDeveloper(developer);

        return param;
    }
}
