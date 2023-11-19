package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.converter.CatConverter;
import org.example.dao.CatDao;
import org.example.exception.EntityNotFoundException;
import org.example.model.dto.CatCreateDto;
import org.example.model.dto.CatDto;
import org.example.model.dto.CatUpdateDto;
import org.example.model.entity.Cat;
import org.example.service.CatService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс CatServiceImpl реализует интерфейс CatService и предоставляет методы для выполнения операций создания, чтения,
 * обновления и удаления объектов типа Cat.
 *
 * @author Витикова Мария
 * @see org.example.service.CatService
 */
@AllArgsConstructor
public class CatServiceImpl implements CatService {

    private final CatDao catDao;
    private final CatConverter catConverter;

    /**
     * Получает объект CatDto по указанному идентификатору.
     * Если объект не найден, выбрасывает исключение EntityNotFoundException.
     *
     * @param id идентификатор объекта Cat
     * @return объект CatDto
     * @throws EntityNotFoundException если объект не найден
     */
    @Override
    public CatDto getById(Long id) {
        return catDao.getById(id).map(catConverter::convert).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Возвращает список всех объектов CatDto.
     * Если список пуст, возвращает пустой список.
     *
     * @return список объектов CatDto
     */
    @Override
    public List<CatDto> getAll() {
        List<Cat> catList = catDao.getAll();
        return catList.isEmpty()
                ? List.of()
                : catList.stream().map(catConverter::convert).collect(Collectors.toList());
    }

    /**
     * Создает новый объект Cat на основе переданного CatCreateDto.
     *
     * @param dto объект CatCreateDto
     * @return созданный объект CatDto
     */
    @Override
    public CatDto create(CatCreateDto dto) {
        return catConverter.convert(catDao.create(catConverter.convert(dto)));
    }

    /**
     * Обновляет существующий объект Cat на основе переданного CatUpdateDto.
     * Если объект не найден, выбрасывает исключение EntityNotFoundException.
     *
     * @param dto объект CatUpdateDto
     * @return обновленный объект CatDto
     * @throws EntityNotFoundException если объект не найден
     */
    @Override
    public CatDto update(CatUpdateDto dto) {
        var cat = catDao.getById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        catConverter.merge(cat, dto);
        return catConverter.convert(catDao.update(cat));
    }

    /**
     * Удаляет объект Cat по указанному идентификатору.
     *
     * @param id идентификатор объекта Cat для удаления
     */
    @Override
    public void delete(Long id) {
        catDao.delete(id);
    }
}
