package ch.bbcag.lor_springapi.services;

import ch.bbcag.lor_springapi.models.Region;
import ch.bbcag.lor_springapi.repositories.RegionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public void deleteById(Integer id) {
        regionRepository.deleteById(id);
    }

    public Region findById(Integer id) {
        return regionRepository.findById(id).orElseThrow();
    }

}
