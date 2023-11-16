package org.example.service;

import org.example.model.dto.CatDto;

import java.util.List;

public interface CatService {

    CatDto getById(Long id);

    List<CatDto> getAll();

    CatDto create(CatDto catDto);

    CatDto update(Long id, CatDto catDto);

    void delete(Long id);
}
