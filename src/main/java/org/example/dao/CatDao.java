package org.example.dao;

import org.example.model.entity.Cat;

import java.util.List;
import java.util.Optional;

public interface CatDao {

    Optional<Cat> getById(Long id);

    List<Cat> getAll();

    Cat create(Cat cat);

    Cat update(Cat cat);

    void delete(Long id);
}
