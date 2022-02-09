package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.models.Region;
import ch.bbcag.lor_springapi.repositories.RarityRepository;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rarity")
public class RarityController {

    @Autowired
    private RarityRepository rarityRepository;

    @Operation(summary = "Find a rarity by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rarity(ies) found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))})})
    @GetMapping
    public Iterable<Rarity> findByName(@Parameter(description = "Rarity to search") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return rarityRepository.findAll();
        }
        return rarityRepository.findByName(name);
    }

    @Operation(summary = "Find a rarity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rarity found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))}),
            @ApiResponse(responseCode = "404", description = "Rarity not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Rarity findById(@Parameter(description = "Id of rarity to get") @PathVariable Integer id) {
        try {
            return rarityRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rarity could not be found");
        }
    }

    @Operation(summary = "Create a new rarity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rarity was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))}),
            @ApiResponse(responseCode = "409", description = "Could not add rarity",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))})})
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new Rarity to create") @Valid @RequestBody Rarity newRarity) {
        try {
            rarityRepository.save(newRarity);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update an already existing rarity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rarity was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))}),
            @ApiResponse(responseCode = "409", description = "Rarity could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The rarity to update") @Valid @RequestBody Rarity rarity) {
        try {
            rarityRepository.save(rarity);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a rarity by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rarity was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rarity.class))}),
            @ApiResponse(responseCode = "404", description = "Rarity could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@Parameter(description = "Id of rarity to delete") @PathVariable Integer id) {
        try {
            rarityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rarity could not be deleted");
        }
    }
}
