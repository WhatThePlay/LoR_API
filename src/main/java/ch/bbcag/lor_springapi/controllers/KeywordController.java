package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Keyword;
import ch.bbcag.lor_springapi.repositories.KeywordRepository;
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
@RequestMapping("/keyword")
public class KeywordController {

    @Autowired
    private KeywordRepository keywordRepository;

    @GetMapping
    public Iterable<Keyword> findByName(@Parameter(description = "Keyword to search") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return keywordRepository.findAll();
        }
        return keywordRepository.findByName(name);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new keyword to create") @Valid @RequestBody Keyword newKeyword) {
        try {
            keywordRepository.save(newKeyword);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The keyword to update") @Valid @RequestBody Keyword keyword) {
        try {
            keywordRepository.save(keyword);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@Parameter(description = "Id of keyword to delete") @PathVariable Integer id) {
        try {
            keywordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keyword could not be deleted");
        }
    }
}
