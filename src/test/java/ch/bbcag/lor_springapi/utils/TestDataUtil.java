package ch.bbcag.lor_springapi.utils;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Keyword;
import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.models.Region;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TestDataUtil {

    public static Region getTestRegion() {
        return getTestRegions().get(0);
    }

    public static List<Region> getTestRegions() {
        List<Region> regions = new ArrayList<>(); //item
        HashSet<Card> cards = new HashSet<>(); // tag

        Rarity rarity = new Rarity();
        rarity.setId(5);
        rarity.setName("None");
        rarity.setCoinCost(0);
        rarity.setShardCost(0);

        Card card = new Card();
        card.setId("id");
        card.setName("name");
        card.setCost(2);
        card.setType("type");
        card.setPicture1("pic1");
        card.setPicture2("pic2");
        card.setRarity(rarity);
//        cards.add(card);

        for (int i = 1; i <= 4; i++) {
            Region region = new Region();
            region.setId(i);
            region.setName("Region" + i);
            region.setIcon("Icon" + i);
            card.getLinkedRegions().add(region);
            region.setLinkedCards(cards);
            regions.add(region);
        }

        return regions;
    }

    public static Rarity getTestRarity() {
        return getTestRarities().get(0);
    }

    public static List<Rarity> getTestRarities() {
        List<Rarity> rarities = new ArrayList<>();
        HashSet<Card> cards = new HashSet<>();

        for (int i = 1; i <= 3; i++) {
            Rarity rarity = new Rarity();
            rarity.setId(i);
            rarity.setName("Rarity" + i);
            rarity.setShardCost(i);
            rarity.setCoinCost(i);
            rarity.setCards(cards);
            rarities.add(rarity);
        }

        return rarities;
    }

    public static Keyword getTestKeyword() {
        return getTestKeywords().get(0);
    }

    public static List<Keyword> getTestKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        HashSet<Card> cards = new HashSet<>();

        for (int i = 1; i <= 4; i++) {
            Keyword keyword = new Keyword();
            keyword.setId(i);
            keyword.setName("Keyword" + i);
            keyword.setDescription("Description" + i);
            keyword.setLinkedCards(cards);
            keywords.add(keyword);
        }

        return keywords;
    }

    public static Card getTestCard() {
        return getTestCards().get(0);
    }

    public static List<Card> getTestCards() {
        List<Card> cards = new ArrayList<>();
        HashSet<Keyword> keywords = new HashSet<>();
        HashSet<Region> regions = new HashSet<>();

        for (int i = 1; i <= 3; i++) {
            Card card = new Card();
            card.setId("ID" + i);
            card.setName("Name" + i);
            card.setCost(i);
            card.setType("Type" + i);
            card.setPicture1("Picture" + i);
            card.setPicture2("Picture" + i);
            card.setAttack(i);
            card.setHealth(i);
            card.setDescription("Description" + i);
            card.setFlavorText("Flavortext" + i);
            card.setArtist("Artist" + i);
            card.setLevelUp("LevelUp" + i);
            card.setSpellSpeed("SpellSpeed" + i);
            card.setCardSet("CardSet" + i);
            card.setSubType("SubType" + i);
            card.setLinkedRegions(regions);
            card.setLinkedKeywords(keywords);
            cards.add(card);
        }

        return cards;
    }

}
