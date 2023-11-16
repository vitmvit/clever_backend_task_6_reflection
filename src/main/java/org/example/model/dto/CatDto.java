package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CatDto {

    private String name;
    private String breed;
    private String color;
    private int age;
}
