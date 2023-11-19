package org.example.service;

import org.example.model.dto.CatCreateDto;
import org.example.model.dto.CatDto;
import org.example.model.dto.CatUpdateDto;

import java.util.List;

public interface CatService {

    CatDto getById(Long id);

    List<CatDto> getAll();

    CatDto create(CatCreateDto dto);

    CatDto update(CatUpdateDto dto);

    void delete(Long id);
}
