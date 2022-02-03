package ch.bbcag.lor_springapi.repositories;

import ch.bbcag.lor_springapi.models.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends CrudRepository<Region, Integer> {

    @Query("SELECT i FROM Region i WHERE i.name LIKE CONCAT('%', :name, '%')")
    Iterable<Region> findByName(@Param("name") String name);

}
