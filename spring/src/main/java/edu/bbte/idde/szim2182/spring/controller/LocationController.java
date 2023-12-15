package edu.bbte.idde.szim2182.spring.controller;

import edu.bbte.idde.szim2182.spring.controlleradvice.NotFoundException;
import edu.bbte.idde.szim2182.spring.dao.LocationDao;
import edu.bbte.idde.szim2182.spring.dto.LocationInDto;
import edu.bbte.idde.szim2182.spring.dto.LocationOutDto;
import edu.bbte.idde.szim2182.spring.mapper.LocationMapper;
import edu.bbte.idde.szim2182.spring.models.Location;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationMapper locationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LocationOutDto> create(@RequestBody @Valid LocationInDto locationDto) {
        Location location = locationMapper.dtoToLocation(locationDto);
        Location savedLocation = locationDao.saveAndFlush(location);
        return ResponseEntity.created(URI.create("/api/locations/" + savedLocation.getId()))
                .body(locationMapper.locationToDto(savedLocation));
    }

    @GetMapping
    public Collection<LocationOutDto> findAll() {
        Collection<Location> locations = locationDao.findAll();
        return locationMapper.locationsToDtos(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationOutDto> findById(@PathVariable("id") Long id) {
        Optional<Location> location = Optional.ofNullable(locationDao.findById(id));
        if (location.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(locationMapper.locationToDto(location.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationOutDto> update(@PathVariable("id") Long id,
                                                 @RequestBody @Valid LocationInDto locationDto) {
        Optional<Location> existingLocation = Optional.ofNullable(locationDao.findById(id));
        if (existingLocation.isEmpty()) {
            throw new NotFoundException();
        }
        Location newLocation = locationMapper.dtoToLocation(locationDto);
        locationDao.update(id, newLocation);
        return ResponseEntity.ok(locationMapper.locationToDto(newLocation));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Optional<Location> location = Optional.ofNullable(locationDao.findById(id));
        if (location.isEmpty()) {
            throw new NotFoundException();
        }
        locationDao.delete(id);
    }
}
