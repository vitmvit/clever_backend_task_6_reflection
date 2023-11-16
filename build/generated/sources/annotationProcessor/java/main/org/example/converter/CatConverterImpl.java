package org.example.converter;

import javax.annotation.processing.Generated;
import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-16T19:26:36+0300",
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
    public Cat convert(CatDto source) {
        if ( source == null ) {
            return null;
        }

        Cat cat = new Cat();

        return cat;
    }

    @Override
    public Cat merge(Cat cat, CatDto catDto) {
        if ( catDto == null ) {
            return null;
        }

        return cat;
    }
}
