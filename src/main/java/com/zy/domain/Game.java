package com.zy.domain;
import lombok.Data;
@Data
public class Game {
    private Integer id;
    private Integer developer_id;
    private Double price;
    private String name;
    private String description;
    private String header_image;
    private String screenshot1;
    private String screenshot2;
    private String screenshot3;
    private Integer is_exist;
    private Long create_time;
}
