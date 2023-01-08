package com.zy.domain;

import lombok.Data;

@Data
public class User {
    /**
     * type
     * 0 管理员
     * 1 玩家
     * 2 开发商
     */
    private Integer id;
    private String username;
    private String password;
    private Integer type;
    private Long create_time;

}
