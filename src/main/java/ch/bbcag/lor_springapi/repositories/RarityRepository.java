package ch.bbcag.lor_springapi.repositories;

import ch.bbcag.lor_springapi.models.Rarity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RarityRepository extends CrudRepository<Rarity, Integer> {

    @Query("SELECT i FROM Rarity i WHERE i.name LIKE CONCAT('%', :name, '%')")
    Iterable<Rarity> findByName(@Param("name") String name);

}
