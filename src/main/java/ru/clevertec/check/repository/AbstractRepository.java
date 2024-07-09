package ru.clevertec.check.repository;

import ru.clevertec.check.exceptions.ResourceNotFoundException;

public interface AbstractRepository<D, T> {
    void save(T t);
    void update(T t, Integer id);
    void deleteById(Integer id);
    D getById(Integer id) throws ResourceNotFoundException;
}
