package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Region;
import ch.bbcag.lor_springapi.repositories.RegionRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping
    public Iterable<Region> findByName(@Parameter(description = "Region to search") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return regionRepository.findAll();
        }
        return regionRepository.findByName(name);
    }

    @GetMapping(path = "{id}")
    public Region findById(@Parameter(description = "Id of region to get") @PathVariable Integer id) {
        try {
            return regionRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region could not be found");
        }
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new Region to create") @Valid @RequestBody Region newRegion) {
        try {
            regionRepository.save(newRegion);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The region to update") @Valid @RequestBody Region region) {
        try {
            regionRepository.save(region);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@Parameter(description = "Id of region to delete") @PathVariable Integer id) {
        try {
            regionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region could not be deleted");
        }
    }

}
