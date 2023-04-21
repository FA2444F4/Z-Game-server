package com.zy.domain;

import lombok.Data;

@Data
public class Essay {
    private Integer id;
    private Integer publisher_id;
    private String publisher_name;
    private Long create_time;
    private String essay_name;
    private String essay_comment;
    private Integer likes;
}
