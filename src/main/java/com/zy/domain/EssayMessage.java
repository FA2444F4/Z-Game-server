package com.zy.domain;

import lombok.Data;

@Data
public class EssayMessage {
    private Integer id;
    private Long messenger_id;
    private String messenger_name;
    private Long create_time;

    private Integer essay_id;

    private String message;
}
