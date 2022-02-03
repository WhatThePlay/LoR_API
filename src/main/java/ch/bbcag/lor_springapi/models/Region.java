package ch.bbcag.lor_springapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String icon;

    @ManyToMany(mappedBy = "linkedRegions")
    @JsonBackReference
    private Set<Card> linkedCards = new HashSet<>();

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<Card> getLinkedCards() {
        return linkedCards;
    }

    public void setLinkedCards(Set<Card> linkedCards) {
        this.linkedCards = linkedCards;
    }
}
