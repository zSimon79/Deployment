package edu.bbte.idde.szim2182.spring.adapter.in.web;

import edu.bbte.idde.szim2182.spring.controlleradvice.NotFoundException;
import edu.bbte.idde.szim2182.spring.application.domain.service.HikeDao;
import edu.bbte.idde.szim2182.spring.application.domain.service.LocationDao;
import edu.bbte.idde.szim2182.spring.application.port.in.HikeInDto;
import edu.bbte.idde.szim2182.spring.application.port.out.HikeOutDto;
import edu.bbte.idde.szim2182.spring.mapper.HikeMapper;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Hike;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Profile("jpa")
@RestController
@RequestMapping("/api/locations")
public class JpaController {

    @Autowired
    private HikeDao hikeDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private HikeMapper hikeMapper;

    @PostMapping("/{locationId}/hikes")
    public ResponseEntity<HikeOutDto> addHikeByLocation(@PathVariable Long locationId,
                                                        @RequestBody HikeInDto hikeDto) {
        Location location = locationDao.findById(locationId)
                .orElseThrow(NotFoundException::new);

        Hike hike = hikeMapper.dtoToHike(hikeDto);
        hike.setLocation(location);
        hike = hikeDao.saveAndFlush(hike);
        return ResponseEntity.status(HttpStatus.CREATED).body(hikeMapper.hikeToDto(hike));
    }

    @GetMapping("/{locationId}/hikes")
    public Collection<HikeOutDto> getHikesByLocation(@PathVariable Long locationId) {
        Optional<Location> optionalLocation = locationDao.findById(locationId);
        if (optionalLocation.isEmpty()) {
            throw new NotFoundException();
        }

        // Retrieve hikes by location ID
        Collection<Hike> hikes = hikeDao.findByLocationId(locationId);
        Collection<HikeOutDto> hikeDtos = hikeMapper.hikesToDtos(hikes);
        return ResponseEntity.ok(hikeDtos).getBody();
    }

    @DeleteMapping("/{locationId}/hikes")
    public ResponseEntity<Void> deleteHikesByLocation(@PathVariable Long locationId) {
        List<Hike> hikes = hikeDao.findByLocationId(locationId);
        for (Hike hike : hikes) {
            hikeDao.deleteById(hike.getId());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{locationId}/hikes/{hikeId}")
    public ResponseEntity<Void> deleteHikeById(@PathVariable Long locationId, @PathVariable Long hikeId) {
        Optional<Hike> hike = hikeDao.findById(hikeId);
        if (hike.isEmpty()) {
            throw new NotFoundException();
        }
        if (!hike.get().getLocation().getId().equals(locationId)) {
            throw new IllegalArgumentException("No hike with given ID found for given location");
        }
        hikeDao.deleteById(hikeId);
        return ResponseEntity.ok().build();
    }
}
