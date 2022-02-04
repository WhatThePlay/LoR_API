package ch.bbcag.lor_springapi.utils;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.models.Region;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TestDataUtil {

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
        cards.add(card);

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

}
