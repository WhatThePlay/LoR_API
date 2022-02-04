package ch.bbcag.lor_springapi.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Rarity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "shardcost")
    private int shardCost;

    @Column(name = "coincost")
    private int coinCost;

    @OneToMany(mappedBy = "rarity", fetch = FetchType.LAZY)
    private Set<Card> cards = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShardCost() {
        return shardCost;
    }

    public void setShardCost(int shardCost) {
        this.shardCost = shardCost;
    }

    public int getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(int coinCost) {
        this.coinCost = coinCost;
    }

    // Don't show every card for every Rarity when getting rarities
//    public Set<Card> getCards() {
//        return cards;
//    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
