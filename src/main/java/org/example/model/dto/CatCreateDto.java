package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.validate.MinLength;
import org.example.validate.NotNegative;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatCreateDto {

    @MinLength
    private String name;

    @MinLength
    private String breed;

    @MinLength
    private String color;

    @NotNegative
    private int age;
}
