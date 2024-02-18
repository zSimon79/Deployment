package edu.bbte.idde.szim2182.spring.adapter.in.web;

import edu.bbte.idde.szim2182.spring.controlleradvice.NotFoundException;
import edu.bbte.idde.szim2182.spring.application.domain.service.HikeDao;
import edu.bbte.idde.szim2182.spring.application.domain.service.LocationDao;
import edu.bbte.idde.szim2182.spring.application.port.in.HikeInDto;
import edu.bbte.idde.szim2182.spring.application.port.out.HikeOutDto;
import edu.bbte.idde.szim2182.spring.mapper.HikeMapper;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Hike;
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
@RequestMapping("/api/hikes")
public class HikeController {

    @Autowired
    private HikeDao hikeDao;

    @Autowired
    private LocationDao locationDao;
    @Autowired
    private HikeMapper hikeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HikeOutDto> create(@RequestBody @Valid HikeInDto hikeDto) {
        Optional<Location> location;
        if (hikeDto.getLocation() != null) {
            location = Optional.of(hikeDto.getLocation());
            // validate location object
        } else {
            location = locationDao.findById(hikeDto.getLocationId());
            // handle case where no location with this ID exists
        }
        if (location.isEmpty()) {
            throw new NotFoundException();
        }

        Hike hike = hikeMapper.dtoToHike(hikeDto);
        hike.setLocation(location.get());
        //setLocation(hike, hikeDto.getLocation().getId());
        hike = hikeDao.saveAndFlush(hike);
        URI createUri = URI.create("/api/hikes/" + hike.getId());
        return ResponseEntity.created(createUri).body(hikeMapper.hikeToDto(hike));
    }

    @GetMapping
    public Collection<HikeOutDto> findAll() {
        Collection<Hike> hikes = hikeDao.findAll();
        return hikeMapper.hikesToDtos(hikes);
    }

    @GetMapping("/{id}")
    public HikeOutDto findById(@PathVariable("id") Long id) {
        Optional<Hike> result = hikeDao.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return hikeMapper.hikeToDto(result.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HikeOutDto> update(@PathVariable("id") Long id,
                                             @RequestBody @Valid HikeInDto hikeDto) {
        Optional<Hike> existingHike = hikeDao.findById(id);
        if (existingHike.isEmpty()) {
            throw new NotFoundException();
        }
        Hike newHike = hikeMapper.dtoToHike(hikeDto);
        hikeDao.update(id, newHike);
        //newHike.setLocation(existingHike.get().getLocation());
        setLocation(newHike, hikeDto.getLocationId());
        newHike.setId(id);
        return ResponseEntity.ok(hikeMapper.hikeToDto(newHike));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<Hike> hike = hikeDao.findById(id);
        if (hike.isEmpty()) {
            throw new NotFoundException();
        }
        hikeDao.deleteById(id);
    }

    private void setLocation(Hike hike, Long locationId) {
        if (locationId != null) {
            Optional<Location> location = locationDao.findById(locationId);
            if (location.isPresent()) {
                hike.setLocation(location.get());
            } else {
                // Handle the case where locationId is provided but not found in DB
                throw new NotFoundException();
            }
        }
    }
}
