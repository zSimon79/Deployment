package edu.bbte.idde.szim2182.spring.application.domain.service;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T entity);

    void update(Long id, T entity);

    void deleteById(Long id);

    T saveAndFlush(T entity);

    //void deleteAll(List<Hike> hikes);
}