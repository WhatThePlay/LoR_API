package ch.bbcag.lor_springapi.repositories;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Rarity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Query("SELECT i FROM Card i WHERE i.name LIKE CONCAT('%', :name, '%')")
    Iterable<Card> findByName(@Param("name") String name);

    // health, cost, attack, rarity

    // Kombinationen bei findUnits
    // 4er Kombination

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.health=:health AND i.attack=:attack")
    Iterable<Card> findByHealthAndAttackAndCostAndRarity(@Param("rarity") String rarity,
                                                         @Param("health") int health,
                                                         @Param("cost") int cost,
                                                         @Param("attack") int attack);

    // 3er Kombinationen (4)

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

    // 2er Kombinationen (6)

    Iterable<Card> findByHealthAndCost(int health, int cost);
    Iterable<Card> findByHealthAndAttack(int health, int attack);
    Iterable<Card> findByAttackAndCost(int attack, int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.health=:health")
    Iterable<Card> findByHealthAndRarity(@Param("rarity") String rarity, @Param("health") int health);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost")
    Iterable<Card> findByCostAndRarity(@Param("rarity") String rarity, @Param("cost") int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.health=:health")
    Iterable<Card> findByAttackAndRarity(@Param("rarity") String rarity, @Param("health") int health);

    // Einzel (4)

    Iterable<Card> findByHealth(int health);
    Iterable<Card> findByAttack(int attack);
    Iterable<Card> findByCost(int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%')")
    Iterable<Card> findByRarity(@Param("rarity") String rarity);

    // Kombinationen bei findSpells
    // spellSpeed, cost, rarity

    // 3er Kombinationen

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.spellSpeed LIKE CONCAT('%', :spellSpeed, '%') AND i.cost=:cost")
    Iterable<Card> findByRarityAndSpellSpeedAndCost(@Param("rarity") String rarity,
                                                    @Param("spellSpeed") String spellSpeed,
                                                    @Param("cost") int cost);

    // 2er Kombinationen

    Iterable<Card> findBySpellSpeedAndCost(String spellSpeed, int cost);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.spellSpeed LIKE CONCAT('%', :spellSpeed, '%')")
    Iterable<Card> findByRarityAndSpellSpeed(@Param("rarity") String rarity,
                                             @Param("spellSpeed") String spellSpeed);

    @Query("SELECT i FROM Card i JOIN i.rarity r WHERE r.name LIKE CONCAT('%', :rarity, '%') AND i.cost=:cost AND i.type='Spell'")
    Iterable<Card> findSpellByRarityAndCost(@Param("rarity") String rarity,
                                            @Param("cost") String cost);

    // übrige 1er Kombinationen

    Iterable<Card> findBySpellSpeed(String spellSpeed);

    // find all spells

    // find all units




}
