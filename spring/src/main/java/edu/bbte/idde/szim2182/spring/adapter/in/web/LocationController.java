package edu.bbte.idde.szim2182.spring.adapter.in.web;

import edu.bbte.idde.szim2182.spring.controlleradvice.NotFoundException;
import edu.bbte.idde.szim2182.spring.application.domain.service.LocationDao;
import edu.bbte.idde.szim2182.spring.application.port.in.LocationInDto;
import edu.bbte.idde.szim2182.spring.application.port.out.LocationOutDto;
import edu.bbte.idde.szim2182.spring.mapper.LocationMapper;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Location;
import jakarta.validation.Valid;
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
        Optional<Location> location = locationDao.findById(id);
        if (location.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(locationMapper.locationToDto(location.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationOutDto> update(@PathVariable("id") Long id,
                                                 @RequestBody @Valid LocationInDto locationDto) {
        Optional<Location> existingLocation = locationDao.findById(id);
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
        Optional<Location> location = locationDao.findById(id);
        if (location.isEmpty()) {
            throw new NotFoundException();
        }
        locationDao.deleteById(id);
    }
}
