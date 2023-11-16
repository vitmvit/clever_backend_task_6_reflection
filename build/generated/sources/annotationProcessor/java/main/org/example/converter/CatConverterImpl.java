package org.example.converter;

import javax.annotation.processing.Generated;
import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-15T23:51:46+0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.9 (GraalVM Community)"
)
public class CatConverterImpl implements CatConverter {

    @Override
    public CatDto convert(Cat source) {
        if ( source == null ) {
            return null;
        }

        CatDto catDto = new CatDto();

        catDto.setId( source.getId() );
        catDto.setName( source.getName() );
        catDto.setBreed( source.getBreed() );
        catDto.setColor( source.getColor() );
        catDto.setAge( source.getAge() );

        return catDto;
    }

    @Override
    public Cat convert(CatDto source) {
        if ( source == null ) {
            return null;
        }

        Cat cat = new Cat();

        cat.setId( source.getId() );
        cat.setName( source.getName() );
        cat.setBreed( source.getBreed() );
        cat.setColor( source.getColor() );
        cat.setAge( source.getAge() );

        return cat;
    }

    @Override
    public Cat merge(Cat cat, CatDto catDto) {
        if ( catDto == null ) {
            return null;
        }

        cat.setId( catDto.getId() );
        cat.setName( catDto.getName() );
        cat.setBreed( catDto.getBreed() );
        cat.setColor( catDto.getColor() );
        cat.setAge( catDto.getAge() );

        return cat;
    }
}
