package edu.bbte.idde.szim2182.backend.dao;

import edu.bbte.idde.szim2182.backend.models.Location;

import java.util.List;

public interface LocationDao extends CrudRepository<Location> {

    @Override
    List<Location> findAll();

    @Override
    Location findById(Long id);

    @Override
    Location create(Location entity);

    @Override
    Location update(Long id, Location entity);

    List<Location> findByName(String name);

    @Override
    void delete(Long id);
}