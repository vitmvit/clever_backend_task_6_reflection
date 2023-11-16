package org.example.exception;

import java.util.UUID;

public class CatNotFoundException extends RuntimeException {

    public CatNotFoundException(Long id) {
        super(String.format("Cat with id: %s not found", id));
    }
}
