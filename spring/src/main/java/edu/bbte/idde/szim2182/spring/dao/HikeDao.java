package edu.bbte.idde.szim2182.spring.dao;

import edu.bbte.idde.szim2182.spring.model.Hike;

import java.util.List;

public interface HikeDao extends Dao<Hike> {

    List<Hike> findByName(String name);


    List<Hike> findByLocationId(Long locationId);


}