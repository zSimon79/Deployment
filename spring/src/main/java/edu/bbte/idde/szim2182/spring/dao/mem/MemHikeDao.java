package edu.bbte.idde.szim2182.spring.dao.mem;

import edu.bbte.idde.szim2182.spring.dao.HikeDao;
import edu.bbte.idde.szim2182.spring.models.Hike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Profile("mem")
public class MemHikeDao implements HikeDao {

    private final ConcurrentHashMap<Long, Hike> hikes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public List<Hike> findAll() {
        log.info("Retrieving all hikes from memory");
        return new ArrayList<>(hikes.values());
    }

    @Override
    public Hike findById(Long id) {
        log.info("Finding hike with ID: {}", id);
        return hikes.get(id);
    }

    @Override
    public Hike create(Hike hike) {
        Long id = idGenerator.incrementAndGet();
        hike.setId(id);
        hikes.put(id, hike);
        log.info("Created a new hike: {}", hike);
        return hike;
    }

    @Override
    public Hike update(Long id, Hike hike) {
        return hikes.compute(id, (key, existingLocation) -> {
            if (existingLocation != null) {
                hike.setId(id);
                log.info("Updated hike with ID {}: {}", id, hike);
                return hike;
            }
            log.error("Hike with ID {} not found for update", id);
            return null;
        });
    }

    @Override
    public Hike saveAndFlush(Hike hike) {
        if (hike.getId() == null || hike.getId() == 0) {
            return create(hike);
        } else {
            return update(hike.getId(), hike);
        }
    }

    @Override
    public List<Hike> findByName(String name) {
        log.info("Finding hikes with name like: {}", name);
        return hikes.values().stream()
                .filter(hike -> hike.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (hikes.containsKey(id)) {
            hikes.remove(id);
            log.info("Deleted hike with ID {}", id);
        } else {
            log.error("Hike with ID {} not found for deletion", id);
        }
    }
}
