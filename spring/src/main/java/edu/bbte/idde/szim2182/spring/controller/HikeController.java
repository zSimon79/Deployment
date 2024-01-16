package edu.bbte.idde.szim2182.spring.controller;

import edu.bbte.idde.szim2182.spring.controlleradvice.NotFoundException;
import edu.bbte.idde.szim2182.spring.dao.HikeDao;
import edu.bbte.idde.szim2182.spring.dto.HikeInDto;
import edu.bbte.idde.szim2182.spring.dto.HikeOutDto;
import edu.bbte.idde.szim2182.spring.mapper.HikeMapper;
import edu.bbte.idde.szim2182.spring.models.Hike;
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
@RequestMapping("/api/hikes")
public class HikeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HikeController.class);

    @Autowired
    private HikeDao hikeDao;
    @Autowired
    private HikeMapper hikeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HikeOutDto> create(@RequestBody @Valid HikeInDto hikeDto) {
        Hike hike = hikeMapper.dtoToHike(hikeDto);
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
        Optional<Hike> result = Optional.ofNullable(hikeDao.findById(id));
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return hikeMapper.hikeToDto(result.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HikeOutDto> update(@PathVariable("id") Long id,
                                             @RequestBody @Valid HikeInDto hikeDto) {
        Optional<Hike> existingHike = Optional.ofNullable(hikeDao.findById(id));
        if (existingHike.isEmpty()) {
            throw new NotFoundException();
        }
        Hike newHike = hikeMapper.dtoToHike(hikeDto);
        hikeDao.update(id, newHike);
        newHike.setLocation(existingHike.get().getLocation());
        newHike.setId(id);
        return ResponseEntity.ok(hikeMapper.hikeToDto(newHike));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<Hike> hike = Optional.ofNullable(hikeDao.findById(id));
        if (hike.isEmpty()) {
            throw new NotFoundException();
        }
        hikeDao.delete(id);
    }

}
