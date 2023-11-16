package org.example.dao.dao;

import org.example.connection.DbConnection;
import org.example.dao.CatDao;
import org.example.dao.impl.CatDaoImpl;
import org.example.dao.util.CatTestData;
import org.example.model.entity.Cat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


public class CatDaoTest {
    private CatDao catDao;

    @BeforeEach
    void setUp() {
        new DbConnection().getConnection();
        catDao = new CatDaoImpl();
        databaseClear();
    }

    @AfterEach
    void after() {
        databaseClear();
    }

    @Test
    void getByIdShouldReturnExpectedCatWithId() {
        Cat expected = databasePrepared();
        Cat actual = catDao.getById(expected.getId()).orElseThrow();
        assertThat(actual).hasFieldOrPropertyWithValue(Cat.Fields.id, expected.getId());
    }

    @Test
    void getByIdShouldReturnExpectedCatWithName() {
        Cat expected = databasePrepared();
        Cat actual = catDao.getById(expected.getId()).orElseThrow();
        assertThat(actual).hasFieldOrPropertyWithValue(Cat.Fields.name, expected.getName());
    }

    @Test
    void getByIdShouldReturnExpectedCatWithBreed() {
        Cat expected = databasePrepared();
        Cat actual = catDao.getById(expected.getId()).orElseThrow();
        assertThat(actual).hasFieldOrPropertyWithValue(Cat.Fields.breed, expected.getBreed());
    }

    @Test
    void getByIdShouldReturnExpectedCatWithColor() {
        Cat expected = databasePrepared();
        Cat actual = catDao.getById(expected.getId()).orElseThrow();
        assertThat(actual).hasFieldOrPropertyWithValue(Cat.Fields.color, expected.getColor());
    }

    @Test
    void getByIdShouldReturnExpectedCatWithAge() {
        Cat expected = databasePrepared();
        Cat actual = catDao.getById(expected.getId()).orElseThrow();
        assertThat(actual).hasFieldOrPropertyWithValue(Cat.Fields.age, expected.getAge());
    }

    @Test
    void getByIdShouldReturnExpectedCatEqualsWithoutId() {
        Cat expected = databasePrepared();
        Cat actual = catDao.getById(expected.getId()).orElseThrow();
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Cat.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Cat.Fields.color, expected.getColor())
                .hasFieldOrPropertyWithValue(Cat.Fields.breed, expected.getBreed())
                .hasFieldOrPropertyWithValue(Cat.Fields.age, expected.getAge());
    }

    @Test
    void getByIdShouldReturnOptionalEmpty() {
        Long id = 2L;
        Optional<Cat> expected = Optional.empty();
        Optional<Cat> actual = catDao.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    void getAllShouldReturnExpectedListCats() {
        List<Cat> expectedList = List.of(
                databasePrepared()
        );
        List<Cat> actualList = catDao.getAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void getAllShouldReturnEmptyListCats() {
        List<Cat> expectedList = catDao.getAll();
        assertEquals(expectedList, List.of());
    }

    @Test
    void createShouldReturnSuccessfullyAddsCatToDatabase() {
        Cat expected = CatTestData.builder().withId(null).build().buildCat();
        assertDoesNotThrow(() -> catDao.create(expected));
        assertNotNull(catDao.create(expected));
    }

    @Test
    public void saveShouldThrowIllegalArgumentExceptionWhenExpectedCatNull() {
        assertThatThrownBy(() -> catDao.create(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateShouldReturnSuccessfullyUpdatesCatInDatabase() {
        Cat expected = databasePrepared();
        Cat update = CatTestData.builder().withId(expected.getId()).build().buildUpdateCat();
        Cat actual = catDao.update(update);
        assertNotNull(actual);
        assertThat(actual.getId()).isEqualTo(update.getId());
        assertThat(actual.getName()).isEqualTo(update.getName());
    }

    @Test
    public void updateShouldThrowIllegalArgumentExceptionWhenExpectedCatNull() {
        assertThatThrownBy(() -> catDao.update(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void delete() {
        Cat cat = databasePrepared();
        catDao.delete(cat.getId());
        Optional<Cat> actual = catDao.getById(cat.getId());
        assertEquals(actual, Optional.empty());
    }

    private Cat databasePrepared() {
        return catDao.create(CatTestData.builder().withId(null).build().buildCat());
    }

    private void databaseClear() {
        for (Cat cat : catDao.getAll()) {
            catDao.delete(cat.getId());
        }
    }
}
