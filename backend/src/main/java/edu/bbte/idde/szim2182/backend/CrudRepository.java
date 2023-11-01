package edu.bbte.idde.szim2182.backend;

import java.util.List;

public interface CrudRepository {

    void create(Hike hike);

    Hike read(int id);

    List<Hike> readAll();

    void update(int id, Hike hike);

    void delete(int id);
}
