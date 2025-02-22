package org.example.converter;

import javax.annotation.processing.Generated;
import org.example.model.dto.CatCreateDto;
import org.example.model.dto.CatDto;
import org.example.model.dto.CatUpdateDto;
import org.example.model.entity.Cat;
import org.example.model.entity.Cat.CatBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-30T23:53:46+0300",
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
    public Cat convert(CatCreateDto source) {
        if ( source == null ) {
            return null;
        }

        CatBuilder cat = Cat.builder();

        cat.name( source.getName() );
        cat.breed( source.getBreed() );
        cat.color( source.getColor() );
        cat.age( source.getAge() );

        return cat.build();
    }

    @Override
    public Cat convert(CatUpdateDto source) {
        if ( source == null ) {
            return null;
        }

        CatBuilder cat = Cat.builder();

        cat.id( source.getId() );
        cat.name( source.getName() );
        cat.breed( source.getBreed() );
        cat.color( source.getColor() );
        cat.age( source.getAge() );

        return cat.build();
    }

    @Override
    public Cat merge(Cat cat, CatUpdateDto dto) {
        if ( dto == null ) {
            return null;
        }

        cat.setId( dto.getId() );
        cat.setName( dto.getName() );
        cat.setBreed( dto.getBreed() );
        cat.setColor( dto.getColor() );
        cat.setAge( dto.getAge() );

        return cat;
    }
}
