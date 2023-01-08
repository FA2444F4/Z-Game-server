package com.zy.service;

import com.zy.domain.Developer;
import com.zy.domain.Player;
import com.zy.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    /**
     * 根据用户名和密码查user
     * @param username
     * @param password
     * @return
     */
    public User getUserByUsernameAndPassword(String username, String password);

    /**
     * 验证username是否唯一
     * @param username
     * @return
     */
    public Boolean verifyUsernameIsUniQue(String username);

    /**
     * 添加user和player
     * @param user
     * @param player
     * @return
     */
    public Boolean addUserAndPlayer(User user, Player player);

    /**
     * 添加用户和开发商
     * @param user
     * @param developer
     * @return
     */
    public Boolean addUserAndDeveloper(User user, Developer developer);



    /**
     * 根据用户名找用户的id
     * @param username
     * @return
     */
    public Integer selectUserIdByUsername(String username);
}
