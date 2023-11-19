package org.example.converter;

import org.example.model.dto.CatCreateDto;
import org.example.model.dto.CatDto;
import org.example.model.dto.CatUpdateDto;
import org.example.model.entity.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CatConverter {

    CatDto convert(Cat source);

    Cat convert(CatCreateDto source);

    Cat convert(CatUpdateDto source);

    Cat merge(@MappingTarget Cat cat, CatUpdateDto dto);
}
