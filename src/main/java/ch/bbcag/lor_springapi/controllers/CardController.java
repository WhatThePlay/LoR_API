package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.repositories.CardRepository;
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
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Operation(summary = "Find a card by name and/or cost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card(s) found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @GetMapping
    public Iterable<Card> findCard(@Parameter(description = "search for a card by name") @RequestParam(required = false) String name,
                                   @Parameter(description = "Search for Cards with a certain Cost") @RequestParam(required = false) Integer cost) {
        if (StringUtils.isBlank(name) && cost == null) {
            return cardRepository.findAll();
        } else if (StringUtils.isBlank(name)) {
            return cardRepository.findByCost(cost);
        } else if (cost == null) {
            return cardRepository.findByName(name);
        } else {
            return cardRepository.findByNameAndCost(name, cost);
        }
    }

    @Operation(summary = "Find a card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Iterable<Card> findById(@Parameter(description = "Id of card to get") @PathVariable String id) {
        try {
            return cardRepository.findById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card could not be found");
        }
    }

    @Operation(summary = "Find units by health/attack/cost/rarity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unit(s) found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @GetMapping("/unit")
    public Iterable<Card> findUnit(@Parameter(description = "Search for a specific Health value.") @RequestParam(required = false) Integer health,
                                   @Parameter(description = "Search for a specific Attack value.") @RequestParam(required = false) Integer attack,
                                   @Parameter(description = "Search for Cards with a certain Cost") @RequestParam(required = false) Integer cost,
                                   @Parameter(description = "Search for a specific Rarity") @RequestParam(required = false) String rarity) {
        if (StringUtils.isBlank(rarity) && health == null && attack == null && cost == null) {
            return cardRepository.findByType("Unit");
        } else if (StringUtils.isBlank(rarity) && attack == null && cost == null) {
            return cardRepository.findByHealth(health);
        } else if (StringUtils.isBlank(rarity) && health == null && cost == null) {
            return cardRepository.findByAttack(attack);
        } else if (StringUtils.isBlank(rarity) && health == null && attack == null) {
            return cardRepository.findByCostAndType(cost, "Unit");
        } else if (health == null && attack == null && cost == null) {
            return cardRepository.findUnitByRarity(rarity);
        } else if (StringUtils.isBlank(rarity) && attack == null) {
            return cardRepository.findByHealthAndCost(health, cost);
        } else if (StringUtils.isBlank(rarity) && cost == null) {
            return cardRepository.findByHealthAndAttack(health, attack);
        } else if (StringUtils.isBlank(rarity) && health == null) {
            return cardRepository.findByAttackAndCost(attack, cost);
        } else if (attack == null && cost == null) {
            return cardRepository.findByHealthAndRarity(rarity, health);
        } else if (health == null && cost == null) {
            return cardRepository.findByAttackAndRarity(rarity, attack);
        } else if (health == null && attack == null) {
            return cardRepository.findByCostAndRarity(rarity, cost);
        } else if (StringUtils.isBlank(rarity)) {
            return cardRepository.findByHealthAndAttackAndCost(health, attack, cost);
        } else if (cost == null) {
            return cardRepository.findByHealthAndAttackAndRarity(rarity, health, attack);
        } else if (attack == null) {
            return cardRepository.findByHealthAndCostAndRarity(rarity, health, cost);
        } else if (health == null) {
            return cardRepository.findByAttackAndCostAndRarity(rarity, cost, attack);
        } else {
            return cardRepository.findByHealthAndAttackAndCostAndRarity(rarity, health, cost, attack);
        }
    }

    @Operation(summary = "Find spells by spellspeed/rarity/cost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spell(s) found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @GetMapping("/spell")
    public Iterable<Card> findSpell(@Parameter(description = "Search for a specific Spell Speed") @RequestParam(required = false) String spellSpeed,
                                    @Parameter(description = "Search for a specific Rarity") @RequestParam(required = false) String rarity,
                                    @Parameter(description = "Search for Cards with a certain Cost") @RequestParam(required = false) Integer cost) {
        if (StringUtils.isBlank(spellSpeed) && StringUtils.isBlank(rarity) && cost == null) {
            return cardRepository.findByType("Spell");
        } else if (StringUtils.isBlank(rarity) && cost == null) {
            return cardRepository.findBySpellSpeed(spellSpeed);
        } else if (StringUtils.isBlank(spellSpeed) && cost == null) {
            return cardRepository.findSpellByRarity(rarity);
        } else if (StringUtils.isBlank(spellSpeed) && StringUtils.isBlank(rarity)) {
            return cardRepository.findByCostAndType(cost, "Spell");
        } else if (StringUtils.isBlank(spellSpeed)) {
            return cardRepository.findSpellByRarityAndCost(rarity, cost);
        } else if (StringUtils.isBlank(rarity)) {
            return cardRepository.findBySpellSpeedAndCost(spellSpeed, cost);
        } else if (cost == null) {
            return cardRepository.findByRarityAndSpellSpeed(rarity, spellSpeed);
        } else {
            return cardRepository.findByRarityAndSpellSpeedAndCost(rarity, spellSpeed, cost);
        }
    }

    @Operation(summary = "Create a new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "409", description = "Could not add card",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@Parameter(description = "The new card to create") @Valid @RequestBody Card newCard) {
        try {
            cardRepository.save(newCard);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update an already existing card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "409", description = "Card could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Parameter(description = "The card to update") @Valid @RequestBody Card card) {
        try {
            cardRepository.save(card);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "404", description = "Card could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@Parameter(description = "Id of card to delete") @PathVariable String id) {
        try {
            cardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keyword could not be deleted");
        }
    }

}
