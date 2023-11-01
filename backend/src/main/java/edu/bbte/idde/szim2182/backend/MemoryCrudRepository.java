package edu.bbte.idde.szim2182.backend;

import java.util.*;
import java.util.concurrent.*;


public class MemoryCrudRepository implements CrudRepository {
    private final ConcurrentMap<Integer, Hike> storage = new ConcurrentHashMap<>();

    @Override
    public void create(Hike hike) {
        storage.put(hike.getId(), hike);
    }

    @Override
    public Hike read(int id) {
        return storage.get(id);
    }

    @Override
    public List<Hike> readAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(int id, Hike hike) {
        storage.put(id, hike);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }
}