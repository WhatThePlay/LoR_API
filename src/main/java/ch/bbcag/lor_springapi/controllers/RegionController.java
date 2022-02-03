package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Region;
import ch.bbcag.lor_springapi.repositories.RegionRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
