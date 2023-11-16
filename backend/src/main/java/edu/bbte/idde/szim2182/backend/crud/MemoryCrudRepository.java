package edu.bbte.idde.szim2182.backend.crud;

import edu.bbte.idde.szim2182.backend.models.Hike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;


public final class MemoryCrudRepository implements CrudRepository {

    private final ConcurrentMap<Long, Hike> storage = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryCrudRepository.class);
    private final AtomicLong idGenerator = new AtomicLong(0);

    private MemoryCrudRepository() {}

    private static class Holder {
        static final MemoryCrudRepository INSTANCE = new MemoryCrudRepository();
    }

    public static MemoryCrudRepository getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Hike create(Hike hike) { //atomic Long legyen id
        Long id = idGenerator.incrementAndGet();
        hike.setId(id);
        storage.put(id, hike);
        logger.info("Created hike: {}", hike);
        return hike;
    }

    @Override
    public Hike read(Long id) {
        return storage.get(id);
    }

    @Override
    public List<Hike> readAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Hike update(Long id, Hike hike) {
        storage.replace(id, hike);
        logger.info("Updated hike with id {}: {}", id, hike);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
        logger.info("Deleted hike with id {}", id);
    }
}