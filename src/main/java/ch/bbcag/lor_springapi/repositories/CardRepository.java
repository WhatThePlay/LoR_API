package ch.bbcag.lor_springapi.repositories;

import ch.bbcag.lor_springapi.models.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CardRepository extends CrudRepository<Card, Integer> {
    // Delete Operation. Because the id of the card is a String and not an Integer
    @Transactional
    void deleteById(String id);

    Iterable<Card> findById(String id);

    // health, cost, attack, rarity

    // findUnits Combinations of following attributes (health, cost, attack, rarity)
    // Combinations of 4 (1)

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.health=:health AND i.attack=:attack")
    Iterable<Card> findByHealthAndAttackAndCostAndRarity(@Param("rarity") String rarity,
                                                         @Param("health") int health,
                                                         @Param("cost") int cost,
                                                         @Param("attack") int attack);

    // Combinations of 3 (4)

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.health=:health AND i.attack=:attack")
    Iterable<Card> findByHealthAndAttackAndRarity(@Param("rarity") String rarity,
                                                  @Param("health") int health,
                                                  @Param("attack") int attack);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.health=:health AND i.attack=:attack")
    Iterable<Card> findByHealthAndCostAndRarity(@Param("rarity") String rarity,
                                                @Param("health") int health,
                                                @Param("cost") int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.health=:health AND i.attack=:attack")
    Iterable<Card> findByAttackAndCostAndRarity(@Param("rarity") String rarity,
                                                @Param("cost") int cost,
                                                @Param("attack") int attack);

    Iterable<Card> findByHealthAndAttackAndCost(int health, int attack, int cost);

    // Combinations of 2 (6)

    Iterable<Card> findByHealthAndCost(int health, int cost);
    Iterable<Card> findByHealthAndAttack(int health, int attack);
    Iterable<Card> findByAttackAndCost(int attack, int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.health=:health")
    Iterable<Card> findByHealthAndRarity(@Param("rarity") String rarity, @Param("health") int health);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.type = 'Unit'")
    Iterable<Card> findByCostAndRarity(@Param("rarity") String rarity, @Param("cost") int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.health=:health")
    Iterable<Card> findByAttackAndRarity(@Param("rarity") String rarity, @Param("health") int health);

    // Single Queries (4)

    Iterable<Card> findByHealth(int health);
    Iterable<Card> findByAttack(int attack);
    Iterable<Card> findByCostAndType(int cost, String type);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.type = 'Unit'")
    Iterable<Card> findUnitByRarity(@Param("rarity") String rarity);

    // findSpells Combinations of following attributes (spellSpeed, cost, rarity)

    // Combination of 3 (1)

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.spellSpeed LIKE CONCAT('%', :spellSpeed, '%') AND i.cost=:cost")
    Iterable<Card> findByRarityAndSpellSpeedAndCost(@Param("rarity") String rarity,
                                                    @Param("spellSpeed") String spellSpeed,
                                                    @Param("cost") int cost);

    // Combinations of 2 (3)

    Iterable<Card> findBySpellSpeedAndCost(String spellSpeed, int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.spellSpeed LIKE CONCAT('%', :spellSpeed, '%')")
    Iterable<Card> findByRarityAndSpellSpeed(@Param("rarity") String rarity,
                                             @Param("spellSpeed") String spellSpeed);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.type = 'Spell'")
    Iterable<Card> findSpellByRarityAndCost(@Param("rarity") String rarity,
                                            @Param("cost") int cost);

    // single Operations

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.type = 'Spell'")
    Iterable<Card> findSpellByRarity(@Param("rarity") String rarity);

    Iterable<Card> findBySpellSpeed(String spellSpeed);

    // find all spells/units
    // and general cost Query

    Iterable<Card> findByType(String type);
    Iterable<Card> findByCost(int cost);
    Iterable<Card> findByNameAndCost(String name, int cost);

    @Query("SELECT i FROM Card i WHERE i.name LIKE CONCAT('%', :name, '%')")
    Iterable<Card> findByName(@Param("name") String name);





}
