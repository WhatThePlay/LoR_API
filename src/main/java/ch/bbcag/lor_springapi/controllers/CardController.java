package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.repositories.CardRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

//    @GetMapping// findByName if you already know the name of the card
//    public Iterable<Card> findByName(@Parameter(description = "Card to search") @RequestParam(required = false) String name) {
//        if (StringUtils.isBlank(name)) {
//            return cardRepository.findAll();
//        }
//        return cardRepository.findByName(name);
//    }

    // health, cost, attack, rarity
    @GetMapping(path = {})
    public Iterable<Card> findUnit(@Parameter(description = "Search for a specific Health value.") @RequestParam(required = false) Integer health,
                                   @Parameter(description = "Search for a specific Attack value.") @RequestParam(required = false) Integer attack,
                                   @Parameter(description = "Search for Cards with a certain Cost") @RequestParam(required = false) Integer cost,
                                   @Parameter(description = "Search for a specific Rarity") @RequestParam(required = false) String rarity){
        if (StringUtils.isBlank(rarity) && health==null && attack==null && cost==null){
            return cardRepository.findAll();
        } else if (StringUtils.isBlank(rarity) && attack==null && cost==null){
            return cardRepository.findByHealth(health);
        } else if (StringUtils.isBlank(rarity) && health==null && cost==null){
            return cardRepository.findByAttack(attack);
        } else if (StringUtils.isBlank(rarity) && health==null && attack==null){
            return cardRepository.findByCost(cost);
        } else if (health==null && attack==null && cost==null){
            return cardRepository.findByRarity(rarity);
        } else if (StringUtils.isBlank(rarity) && attack==null){
            return cardRepository.findByHealthAndCost(health, cost);
        } else if (StringUtils.isBlank(rarity) && cost==null){
            return cardRepository.findByHealthAndAttack(health, attack);
        } else if (StringUtils.isBlank(rarity) && health==null){
            return cardRepository.findByAttackAndCost(attack, cost);
        } else if (attack==null && cost==null){
            return cardRepository.findByHealthAndRarity(rarity, health);
        } else if (health==null && cost==null){
            return cardRepository.findByAttackAndRarity(rarity, attack);
        } else if (health==null && attack==null){
            return cardRepository.findByCostAndRarity(rarity, cost);
        } else if (StringUtils.isBlank(rarity)){
            return cardRepository.findByHealthAndAttackAndCost(health, attack, cost);
        } else if (cost==null){
            return cardRepository.findByHealthAndAttackAndRarity(rarity, health, attack);
        } else if (attack==null){
            return cardRepository.findByHealthAndCostAndRarity(rarity, health, cost);
        } else if (health==null){
            return cardRepository.findByAttackAndCostAndRarity(rarity, cost, attack);
        } else{
            return cardRepository.findByHealthAndAttackAndCostAndRarity(rarity, health, cost, attack);
        }
    }

    // spellSpeed, cost, rarity
    @GetMapping
    public Iterable<Card> findSpell(@Parameter(description = "Search for a specific Spell Spedd") @RequestParam(required = false) String spellSpeed,
                                    @Parameter(description = "Search for a specific Rarity") @RequestParam(required = false) String rarity,
                                    @Parameter(description = "Search for Cards with a certain Cost") @RequestParam(required = false) Integer cost){
        if(StringUtils.isBlank(spellSpeed) && StringUtils.isBlank(rarity) && cost == null){
            return cardRepository.findAll();
        } else if(StringUtils.isBlank(rarity) && cost == null){
            return cardRepository.findBySpellSpeed(spellSpeed);
        } else if(StringUtils.isBlank(spellSpeed) && cost == null){
            return cardRepository.findByRarity(rarity);
        } else if(StringUtils.isBlank(spellSpeed) && StringUtils.isBlank(rarity)){
            return cardRepository.findByCost(cost);
        } else if(StringUtils.isBlank(spellSpeed)){
            return cardRepository.
        } else if(StringUtils.isBlank(rarity)){
            return cardRepository.findBySpellSpeedAndCost(spellSpeed, cost);
        } else if(cost == null){
            return cardRepository.findByRarityAndSpellSpeed(rarity, spellSpeed);
        } else{
            return cardRepository.findByRarityAndSpellSpeedAndCost(rarity, spellSpeed, cost);
        }
    }
}
