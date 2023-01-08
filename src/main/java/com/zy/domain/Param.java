package com.zy.domain;

import lombok.Data;

@Data
public class Param {
    private User user;
    private Administrator administrator;
    private Player player;
    private Developer developer;
}
