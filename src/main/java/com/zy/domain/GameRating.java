package com.zy.domain;

import lombok.Data;

@Data
public class GameRating {
    private Integer game_id;
    private Integer player_id;
    private Integer rating;
    private String comment;
    private Long create_time;
    private Integer is_exist;
}
