package org.example.service;

import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;

import java.util.List;

public interface CatService {

    CatDto getById(Long id);

    List<CatDto> getAll();

    CatDto create(CatDto catDto);

    CatDto update(CatDto catDto);

    void delete(Long id);
}
