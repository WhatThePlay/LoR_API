package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Region;
import ch.bbcag.lor_springapi.repositories.RegionRepository;
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
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @Operation(summary = "Find a region by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region(s) found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))})})
    @GetMapping
    public Iterable<Region> findByName(@Parameter(description = "Region to search") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return regionRepository.findAll();
        }
        return regionRepository.findByName(name);
    }

    @Operation(summary = "Find a region by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))}),
            @ApiResponse(responseCode = "404", description = "Region not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Region findById(@Parameter(description = "Id of region to get") @PathVariable Integer id) {
        try {
            return regionRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region could not be found");
        }
    }

    @Operation(summary = "Create a new region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Region was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))}),
            @ApiResponse(responseCode = "409", description = "Could not add region",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))})})
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new Region to create") @Valid @RequestBody Region newRegion) {
        try {
            regionRepository.save(newRegion);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update an already existing region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))}),
            @ApiResponse(responseCode = "409", description = "Region could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The region to update") @Valid @RequestBody Region region) {
        try {
            regionRepository.save(region);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a region by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Region.class))}),
            @ApiResponse(responseCode = "404", description = "Region could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void deleteById(@Parameter(description = "Id of region to delete") @PathVariable Integer id) {
        try {
            regionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Region could not be deleted");
        }
    }

}
