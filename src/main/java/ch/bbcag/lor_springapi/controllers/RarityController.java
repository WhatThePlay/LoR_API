package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.repositories.RarityRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/rarity")
public class RarityController {

    @Autowired
    private RarityRepository rarityRepository;

    @GetMapping
    public Iterable<Rarity> findByName(@Parameter(description = "Rarity to search") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return rarityRepository.findAll();
        }
        return rarityRepository.findByName(name);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new Rarity to create") @Valid @RequestBody Rarity newRarity) {
        try {
            rarityRepository.save(newRarity);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The rarity to update") @Valid @RequestBody Rarity rarity) {
        try {
            rarityRepository.save(rarity);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@Parameter(description = "Id of rarity to delete") @PathVariable Integer id) {
        try {
            rarityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rarity could not be deleted");
        }
    }
}
