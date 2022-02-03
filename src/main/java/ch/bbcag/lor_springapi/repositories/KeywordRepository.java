package ch.bbcag.lor_springapi.repositories;

import ch.bbcag.lor_springapi.models.Keyword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KeywordRepository extends CrudRepository<Keyword, Integer> {

    @Query("SELECT i FROM Keyword i WHERE i.name LIKE CONCAT('%', :name, '%')")
    Iterable<Keyword> findByName(@Param("name") String name);

}
