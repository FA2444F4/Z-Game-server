package com.zy.domain;
import lombok.Data;
@Data
public class Game {
    private Integer id;
    private Long developer_id;
    private Double price;
    private String name;
    private String description;
    private String header_image;
    private String screenshot1;
    private String screenshot2;
    private String screenshot3;
    private Integer is_exist;
    private Long create_time;
    public Game(Integer id, Long developer_id, Double price, String name, String description, String header_image, String screenshot1, String screenshot2, String screenshot3, Integer is_exist, Long create_time) {
        this.id = id;
        this.name = name;
        this.developer_id = developer_id;
        this.price = price;
        this.create_time = create_time;
        this.description = description;
        this.header_image = header_image;
        this.screenshot1 = screenshot1;
        this.screenshot2 = screenshot2;
        this.screenshot3 = screenshot3;
        this.is_exist = is_exist;
    }
    public Game(){

    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", developer_id=" + developer_id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", header_image='" + header_image + '\'' +
                ", screenshot1='" + screenshot1 + '\'' +
                ", screenshot2='" + screenshot2 + '\'' +
                ", screenshot3='" + screenshot3 + '\'' +
                ", is_exist=" + is_exist +
                ", create_time=" + create_time +
                '}';
    }


}
