package edu.bbte.idde.szim2182.spring.dao.mem;

import edu.bbte.idde.szim2182.spring.dao.HikeDao;
import edu.bbte.idde.szim2182.spring.model.Hike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Optional<Hike> findById(Long id) {
        Hike hike = this.hikes.get(id);
        if (hike != null) {
            log.info("Hike with {} id has found!", id);
            return Optional.of(hike);
        }
        log.info("Hike with {} id has not found!", id);
        return Optional.empty();
    }

    @Override
    public Hike save(Hike hike) {
        Long id = idGenerator.incrementAndGet();
        hike.setId(id);
        hikes.put(id, hike);
        log.info("Created a new hike: {}", hike);
        return hike;
    }

    @Override
    public void update(Long id, Hike hike) {
        hikes.compute(id, (key, existingLocation) -> {
            if (existingLocation != null) {
                hike.setId(id);
                log.info("Updated hike with ID {}: {}", id, hike);

            }
            log.error("Hike with ID {} not found for update", id);
            return null;
        });
    }

    @Override
    public Hike saveAndFlush(Hike hike) {
        if (hike.getId() == null || hike.getId() == 0) {
            return save(hike);
        } else {
            update(hike.getId(), hike);
            return hike;
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
    public List<Hike> findByLocationId(Long locationId) {
        log.info("Finding hikes for location ID: {}", locationId);
        return hikes.values().stream()
                .filter(hike -> hike.getLocation() != null && hike.getLocation().getId().equals(locationId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (hikes.containsKey(id)) {
            hikes.remove(id);
            log.info("Deleted hike with ID {}", id);
        } else {
            log.error("Hike with ID {} not found for deletion", id);
        }
    }

}
