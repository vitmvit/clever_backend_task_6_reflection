package org.example.dao.util;

import lombok.Builder;
import lombok.Data;
import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;

import static org.example.dao.constant.Constant.*;

@Builder(setterPrefix = "with")
@Data
public class CatTestData {
    @Builder.Default
    private Long id = CAT_ID;

    @Builder.Default
    private String name = CAT_NAME;

    @Builder.Default
    private String breed = CAT_BREED;

    @Builder.Default
    private String color = CAT_COLOR;

    @Builder.Default
    private int age = CAT_AGE;

    public Cat buildCat() {
        return new Cat(id, name, breed, color, age);
    }

    public Cat buildUpdateCat() {
        return new Cat(id, name, breed + " new", color + "new", age);
    }

    public CatDto buildCatDto() {
        return new CatDto(name, breed, color, age);
    }
}
