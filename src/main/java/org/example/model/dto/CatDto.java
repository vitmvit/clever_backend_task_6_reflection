package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.example.validate.MinLength;
import org.example.validate.NotNegative;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CatDto {

    private Long id;

    @MinLength
    private String name;

    @MinLength
    private String breed;

    @MinLength
    private String color;

    @NotNegative
    private int age;
}
