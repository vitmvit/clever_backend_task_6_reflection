package org.example.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatDto {

    private Long id;
    private String name;
    private String breed;
    private String color;
    private int age;
}
