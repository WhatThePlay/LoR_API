package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Keyword;
import ch.bbcag.lor_springapi.models.Region;
import ch.bbcag.lor_springapi.repositories.KeywordRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Key;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/keyword")
public class KeywordController {

    @Autowired
    private KeywordRepository keywordRepository;

    @Operation(summary = "Find a keyword by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword(s) found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))})})
    @GetMapping
    public Iterable<Keyword> findByName(@Parameter(description = "Keyword to search") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return keywordRepository.findAll();
        }
        return keywordRepository.findByName(name);
    }

    @Operation(summary = "Find a keyword by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))}),
            @ApiResponse(responseCode = "404", description = "Keyword not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Keyword findById(@Parameter(description = "Id of keyword to get") @PathVariable Integer id) {
        try {
            return keywordRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keyword could not be found");
        }
    }

    @Operation(summary = "Create a new keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Keyword was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))}),
            @ApiResponse(responseCode = "409", description = "Could not add keyword",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))})})
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new keyword to create") @Valid @RequestBody Keyword newKeyword) {
        try {
            keywordRepository.save(newKeyword);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update an already existing keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))}),
            @ApiResponse(responseCode = "409", description = "Keyword could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The keyword to update") @Valid @RequestBody Keyword keyword) {
        try {
            keywordRepository.save(keyword);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a keyword by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Keyword.class))}),
            @ApiResponse(responseCode = "404", description = "Keyword could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@Parameter(description = "Id of keyword to delete") @PathVariable Integer id) {
        try {
            keywordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keyword could not be deleted");
        }
    }
}
