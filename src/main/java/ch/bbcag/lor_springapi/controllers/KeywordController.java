package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Keyword;
import ch.bbcag.lor_springapi.repositories.KeywordRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
