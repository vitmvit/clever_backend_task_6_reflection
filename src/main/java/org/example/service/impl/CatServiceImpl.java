package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.converter.CatConverter;
import org.example.dao.CatDao;
import org.example.exception.EntityNotFoundException;
import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;
import org.example.service.CatService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CatServiceImpl implements CatService {

    private final CatConverter catConverter;
    private final CatDao catDao;

    @Override
    public CatDto getById(Long id) {
        return catDao.getById(id).map(catConverter::convert).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CatDto> getAll() {
        List<Cat> catList = catDao.getAll();
        return catList.isEmpty()
                ? List.of()
                : catList.stream().map(catConverter::convert).collect(Collectors.toList());
    }

    @Override
    public CatDto create(CatDto catDto) {
        return catConverter.convert(catDao.create(catConverter.convert(catDto)));
    }

    @Override
    public CatDto update(Long id, CatDto catDto) {
        var cat = catDao.getById(id).orElseThrow(EntityNotFoundException::new);
        catConverter.merge(cat, catDto);
        return catConverter.convert(catDao.update(cat));
    }

    @Override
    public void delete(Long id) {
        catDao.delete(id);
    }
}
