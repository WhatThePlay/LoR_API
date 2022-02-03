package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.repositories.RarityRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
