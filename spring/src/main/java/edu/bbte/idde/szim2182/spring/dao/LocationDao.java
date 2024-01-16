package edu.bbte.idde.szim2182.spring.dao;

import edu.bbte.idde.szim2182.spring.models.Location;

import java.util.List;

public interface LocationDao extends Dao<Location> {
    List<Location> findByName(String name);

}