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
    private Integer id;
    private Integer status;
    private Double deposit;
    private String nick_name;

    private String name;
    private Integer sex;
    private String phone;
}
