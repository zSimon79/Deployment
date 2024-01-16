package edu.bbte.idde.szim2182.spring.dao;

import java.util.List;

public interface Dao<T> {
    List<T> findAll();

    T findById(Long id);

    T create(T entity);

    T update(Long id, T entity);

    void delete(Long id);

    T saveAndFlush(T entity);


}