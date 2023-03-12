package com.zy.domain;

import lombok.Data;

import java.util.List;

@Data
public class Param {
    private User user;
    private Administrator administrator;
    private Player player;
    private Developer developer;
    private List<Tag> tagList;
}
