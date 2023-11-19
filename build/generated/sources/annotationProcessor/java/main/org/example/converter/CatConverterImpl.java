package org.example.converter;

import javax.annotation.processing.Generated;
import org.example.model.dto.CatCreateDto;
import org.example.model.dto.CatDto;
import org.example.model.dto.CatUpdateDto;
import org.example.model.entity.Cat;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-19T22:26:30+0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.9 (GraalVM Community)"
)
public class CatConverterImpl implements CatConverter {

    @Override
    public CatDto convert(Cat source) {
        if ( source == null ) {
            return null;
        }

        CatDto catDto = new CatDto();

        return catDto;
    }

    @Override
    public Cat convert(CatCreateDto source) {
        if ( source == null ) {
            return null;
        }

        Cat cat = new Cat();

        return cat;
    }

    @Override
    public Cat convert(CatUpdateDto source) {
        if ( source == null ) {
            return null;
        }

        Cat cat = new Cat();

        return cat;
    }

    @Override
    public Cat merge(Cat cat, CatUpdateDto dto) {
        if ( dto == null ) {
            return null;
        }

        return cat;
    }
}
