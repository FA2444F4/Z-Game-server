package com.zy.domain;

import lombok.Data;

@Data
public class GameRating {
    private Integer game_id;
    private Long player_id;
    private Integer rating;
    private String comment;
    private Long create_time;
    private Integer is_exist;
    public GameRating(Integer game_id, Long player_id, Integer rating, String comment, Long create_time, Integer is_exist) {
        this.game_id = game_id;
        this.player_id = player_id;
        this.rating = rating;
        this.comment = comment;
        this.create_time = create_time;
        this.is_exist = is_exist;
    }

    @Override
    public String toString() {
        return "GameRating{" +
                "game_id=" + game_id +
                ", player_id=" + player_id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", create_time=" + create_time +
                ", is_exist=" + is_exist +
                '}';
    }
}
