package org.example.dao.service;

import org.example.converter.CatConverter;
import org.example.dao.CatDao;
import org.example.dao.util.CatTestData;
import org.example.exception.EntityNotFoundException;
import org.example.model.dto.CatDto;
import org.example.model.entity.Cat;
import org.example.service.impl.CatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatServiceTest {

    @Mock
    private CatDao catDao;

    @Mock
    private CatConverter catConverter;

    @InjectMocks
    private CatServiceImpl catService;

    @Captor
    private ArgumentCaptor<Cat> catArgumentCaptor;

    @Test
    void getByIdShouldReturnExpectedCatWhenFound() {
        Cat expected = CatTestData.builder().build().buildCat();
        CatDto catDto = CatTestData.builder().build().buildCatDto();
        Long id = expected.getId();
        when(catDao.getById(id)).thenReturn(Optional.of(expected));
        when(catConverter.convert(expected)).thenReturn(catDto);
        CatDto actual = catService.getById(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Cat.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Cat.Fields.breed, expected.getBreed())
                .hasFieldOrPropertyWithValue(Cat.Fields.color, expected.getColor())
                .hasFieldOrPropertyWithValue(Cat.Fields.age, expected.getAge());
    }

    @Test
    void getByIdShouldThrowCatNotFoundExceptionWhenNotFound() {
        var exception = assertThrows(Exception.class, () -> catService.getById(null));
        assertEquals(exception.getClass(), EntityNotFoundException.class);
    }

    @Test
    void getAllShouldReturnExpectedListCats() {
        List<Cat> expectedList = catDao.getAll();
        when(catDao.getAll()).thenReturn(expectedList);
        List<CatDto> actualList = catService.getAll();
        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    void getAllShouldThrowCatNotFoundExceptionWhenEmptyListCats() {
        List<Cat> catList = new ArrayList<>();
        when(catDao.getAll()).thenReturn(catList);
        List<CatDto> actualList = catService.getAll();
        assertEquals(0, actualList.size());
        verify(catDao, times(1)).getAll();
    }

    @Test
    void createShouldInvokeRepositoryWithoutCatId() {
        Cat catToSave = CatTestData.builder().withId(null).build().buildCat();
        Cat expected = CatTestData.builder().build().buildCat();
        CatDto catDto = CatTestData.builder().withId(null).build().buildCatDto();
        doReturn(expected).when(catDao).create(catToSave);
        when(catConverter.convert(catDto)).thenReturn(catToSave);
        catService.create(catDto);
        verify(catDao).create(catArgumentCaptor.capture());
        assertThat(catArgumentCaptor.getValue()).hasFieldOrPropertyWithValue(Cat.Fields.id, null);
    }

    @Test
    void updateShouldThrowCatNotFoundExceptionWhenCatNotFound() {
        Long id = CatTestData.builder().build().getId();
        CatDto catDto = CatTestData.builder().build().buildCatDto();
        when(catDao.getById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> catService.update(id, catDto));
        verify(catDao, times(1)).getById(id);
    }

    @Test
    void updateShouldCallsMergeAndSaveWhenCatFound() {
        Long id = CatTestData.builder().build().getId();
        CatDto catDto = CatTestData.builder().build().buildCatDto();
        Cat cat = new Cat();
        when(catDao.getById(id)).thenReturn(Optional.of(cat));
        catService.update(id, catDto);
        verify(catDao, times(1)).getById(id);
        verify(catConverter, times(1)).merge(catArgumentCaptor.capture(), eq(catDto));
        assertSame(cat, catArgumentCaptor.getValue());
        verify(catDao, times(1)).update(cat);
    }

    @Test
    void delete() {
        Long id = CatTestData.builder().build().getId();
        catService.delete(id);
        verify(catDao).delete(id);
    }
}
