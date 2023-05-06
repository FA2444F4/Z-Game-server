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
    private Long id;
    private String username;
    private String password;
    private Integer type;
    private Long create_time;
    public User(Long id, String username, String password, Integer type, Long create_time) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", create_time=" + create_time +
                '}';
    }

}
