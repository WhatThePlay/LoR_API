package ch.bbcag.lor_springapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {

    @Id
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private int cost;

    @NotNull
    private String type;

    @NotNull
    private String picture1;

    @NotNull
    private String picture2;

    private int attack;
    private int health;
    private String description;
    @Column(name="flavortext")
    private String flavorText;
    private String artist;
    @Column(name="levelup")
    private String levelUp;
    @Column(name="spellspeed")
    private String spellSpeed;
    @Column(name="cardset")
    private String cardSet;
    @Column(name="subtype")
    private String subType;

    @ManyToOne
    @JoinColumn(name = "Rarity_id")
    @JsonBackReference
    private Rarity rarity;

    @ManyToMany
    @JoinTable(
            name = "card_has_region",
            joinColumns = @JoinColumn(name = "Card_id"),
            inverseJoinColumns = @JoinColumn(name = "Region_id"))
    private Set<Region> linkedRegions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "card_has_keyword",
            joinColumns = @JoinColumn(name = "Card_id"),
            inverseJoinColumns = @JoinColumn(name = "Keyword_id"))
    private Set<Region> linkedKeywords = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLevelUp() {
        return levelUp;
    }

    public void setLevelUp(String levelUp) {
        this.levelUp = levelUp;
    }

    public String getSpellSpeed() {
        return spellSpeed;
    }

    public void setSpellSpeed(String spellSpeed) {
        this.spellSpeed = spellSpeed;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Set<Region> getLinkedRegions() {
        return linkedRegions;
    }

    public void setLinkedRegions(Set<Region> linkedRegions) {
        this.linkedRegions = linkedRegions;
    }

    public Set<Region> getLinkedKeywords() {
        return linkedKeywords;
    }

    public void setLinkedKeywords(Set<Region> linkedKeywords) {
        this.linkedKeywords = linkedKeywords;
    }
}
