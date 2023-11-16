package org.example.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cat {

    private Long id;
    private String name;
    private String breed;
    private String color;
    private int age;
}
