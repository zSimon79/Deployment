package edu.bbte.idde.szim2182.backend.dao;

import edu.bbte.idde.szim2182.backend.models.Hike;

import java.util.List;

public interface HikeDao extends CrudRepository<Hike> {

    @Override
    List<Hike> findAll();

    @Override
    Hike findById(Long id);

    @Override
    Hike create(Hike hike);

    @Override
    Hike update(Long id, Hike hike);

    List<Hike> findByName(String name);

    @Override
    void delete(Long id);
}