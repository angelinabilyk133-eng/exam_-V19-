package org.example;

import java.util.Optional;
import java.util.List;

public interface Repository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
}

