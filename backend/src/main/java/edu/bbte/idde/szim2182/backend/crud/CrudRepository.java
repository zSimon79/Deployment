package edu.bbte.idde.szim2182.backend.crud;

import edu.bbte.idde.szim2182.backend.models.Hike;

import java.util.List;

public interface CrudRepository {

    Hike create(Hike hike);

    Hike read(Long id);

    List<Hike> readAll(); // inkább collection

    Hike update(Long id, Hike hike);

    void delete(Long id);


}
