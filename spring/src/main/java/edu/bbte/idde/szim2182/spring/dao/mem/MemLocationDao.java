package edu.bbte.idde.szim2182.spring.dao.mem;

import edu.bbte.idde.szim2182.spring.dao.LocationDao;
import edu.bbte.idde.szim2182.spring.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@Profile("mem")
public class MemLocationDao implements LocationDao {

    private final ConcurrentHashMap<Long, Location> locations = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public List<Location> findAll() {
        log.info("Retrieving all locations from memory");
        return new ArrayList<>(locations.values());
    }

    @Override
    public Optional<Location> findById(Long id) {
        Location location = this.locations.get(id);
        if (location != null) {
            log.info("Location with {} id has found!", id);
            return Optional.of(location);
        }
        log.info("Location with {} id has not found!", id);
        return Optional.empty();
    }

    @Override
    public Location save(Location location) {
        Long id = idGenerator.incrementAndGet();
        location.setId(id);
        locations.put(id, location);
        log.info("Created a new location: {}", location);
        return location;
    }

    @Override
    public void update(Long id, Location location) {
        locations.compute(id, (key, existingLocation) -> {
            if (existingLocation != null) {
                location.setId(id);
                log.info("Updated location with ID {}: {}", id, location);

            }
            log.error("Location with ID {} not found for update", id);
            return null;
        });
    }

    @Override
    public Location saveAndFlush(Location location) {
        if (location.getId() == null || location.getId() == 0) {
            return save(location);
        } else {
            update(location.getId(), location);
            return location;
        }
    }

    @Override
    public void deleteById(Long id) {
        if (locations.containsKey(id)) {
            locations.remove(id);
            log.info("Deleted location with ID {}", id);
        } else {
            log.error("Location with ID {} not found for deletion", id);
        }
    }
}
