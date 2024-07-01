package org.example.observer;

import org.example.model.dto.CatDto;

public interface Observer {
    void update(CatDto catDto);
}
