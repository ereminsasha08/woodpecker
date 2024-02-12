package org.woodpecker.service;

import java.util.List;

public interface BaseCrudService<T> {

    List<T> getAll();

    T getById(Integer id);

    T create(T creadet);

    T update(T updated, Integer id);

    void deleteById(Integer id);
}
