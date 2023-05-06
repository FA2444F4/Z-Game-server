package com.zy.domain;

import lombok.Data;

@Data
public class Tag {
    private Integer id;
    private String name;
    private String description;

    public Tag(Integer id,String name,String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }
}
