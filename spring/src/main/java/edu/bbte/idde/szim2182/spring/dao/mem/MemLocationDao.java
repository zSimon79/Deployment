package edu.bbte.idde.szim2182.spring.dao.mem;

import edu.bbte.idde.szim2182.spring.dao.LocationDao;
import edu.bbte.idde.szim2182.spring.models.Location;
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
public class MemLocationDao implements LocationDao {

    private final ConcurrentHashMap<Long, Location> locations = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public List<Location> findAll() {
        log.info("Retrieving all locations from memory");
        return new ArrayList<>(locations.values());
    }

    @Override
    public Location findById(Long id) {
        log.info("Finding location with ID: {}", id);
        return locations.get(id);
    }

    @Override
    public Location create(Location location) {
        Long id = idGenerator.incrementAndGet();
        location.setId(id);
        locations.put(id, location);
        log.info("Created a new location: {}", location);
        return location;
    }

    @Override
    public Location update(Long id, Location location) {
        return locations.compute(id, (key, existingLocation) -> {
            if (existingLocation != null) {
                location.setId(id);
                log.info("Updated location with ID {}: {}", id, location);

                return location;
            }
            log.error("Location with ID {} not found for update", id);
            return null;
        });
    }

    @Override
    public Location saveAndFlush(Location location) {
        if (location.getId() == null || location.getId() == 0) {
            return create(location);
        } else {
            return update(location.getId(), location);
        }
    }

    @Override
    public List<Location> findByName(String name) {
        log.info("Finding locations with name like: {}", name);
        return locations.values().stream()
                .filter(location -> location.getStartPoint().contains(name) || location.getEndPoint().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (locations.containsKey(id)) {
            locations.remove(id);
            log.info("Deleted location with ID {}", id);
        } else {
            log.error("Location with ID {} not found for deletion", id);
        }
    }
}
