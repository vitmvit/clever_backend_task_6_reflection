package org.example.exception;

public class CatNotFoundException extends RuntimeException {

    public CatNotFoundException(Long id) {
        super(String.format("Cat with id: %s not found", id));
    }
}
