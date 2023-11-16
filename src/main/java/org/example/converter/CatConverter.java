package org.example.converter;

import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CatConverter {

    CatDto convert(Cat source);

    Cat convert(CatDto source);

    Cat merge(@MappingTarget Cat cat, CatDto catDto);
}
