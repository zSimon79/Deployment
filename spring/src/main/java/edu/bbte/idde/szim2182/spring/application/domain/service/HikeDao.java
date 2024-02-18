package edu.bbte.idde.szim2182.spring.application.domain.service;

import edu.bbte.idde.szim2182.spring.application.domain.entity.Hike;

import java.util.List;

public interface HikeDao extends Dao<Hike> {

    List<Hike> findByName(String name);


    List<Hike> findByLocationId(Long locationId);


}