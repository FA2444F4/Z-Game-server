package com.zy.domain;

import lombok.Data;

@Data
public class Player {
    /**
     * status
     * 0正常
     * 1封禁
     *
     */
    private Long id;
    private Integer status;
    private Double deposit;
    private String nick_name;

    private String name;
    private Integer sex;
    private String phone;

    public Player(Long id, Integer status, Double deposit, String nick_name, String name, Integer sex, String phone) {
        this.id = id;
        this.status = status;
        this.deposit = deposit;
        this.nick_name = nick_name;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", status=" + status +
                ", deposit=" + deposit +
                ", nick_name='" + nick_name + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                '}';
    }
}
