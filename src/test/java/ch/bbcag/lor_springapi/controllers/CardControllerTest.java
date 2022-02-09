package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Card;
import ch.bbcag.lor_springapi.repositories.CardRepository;
import ch.bbcag.lor_springapi.utils.TestDataUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static ch.bbcag.lor_springapi.utils.TestDataUtil.getTestCards;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc(addFilters = false)
class CardControllerTest {

    private static final String JSON_ALL_Cards = """
            [
              {
                "id": "ID1",
                "name": "Name1",
                "cost": 1,
                "type": "Type1",
                "picture1": "Picture1",
                "picture2": "Picture1",
                "attack": 1,
                "health": 1,
                "description": "Description1",
                "flavorText": "Flavortext1",
                "artist": "Artist1",
                "levelUp": "LevelUp1",
                "spellSpeed": "SpellSpeed1",
                "cardSet": "CardSet1",
                "subType": "SubType1",
                "linkedRegions": [],
                "linkedKeywords": []
              },
              {
                "id": "ID2",
                "name": "Name2",
                "cost": 2,
                "type": "Type2",
                "picture1": "Picture2",
                "picture2": "Picture2",
                "attack": 2,
                "health": 2,
                "description": "Description2",
                "flavorText": "Flavortext2",
                "artist": "Artist2",
                "levelUp": "LevelUp2",
                "spellSpeed": "SpellSpeed2",
                "cardSet": "CardSet2",
                "subType": "SubType2",
                "linkedRegions": [],
                "linkedKeywords": []
              },
              {
                "id": "ID3",
                "name": "Name3",
                "cost": 3,
                "type": "Type3",
                "picture1": "Picture3",
                "picture2": "Picture3",
                "attack": 3,
                "health": 3,
                "description": "Description3",
                "flavorText": "Flavortext3",
                "artist": "Artist3",
                "levelUp": "LevelUp3",
                "spellSpeed": "SpellSpeed3",
                "cardSet": "CardSet3",
                "subType": "SubType3",
                "linkedRegions": [],
                "linkedKeywords": []
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardController cardController;

    @MockBean
    private CardRepository cardRepository;

    @Test //pass
    public void checkGet_whenNoParam_thenAllCardsAreReturned() throws Exception {
        doReturn(getTestCards()).when(cardController).findCard(null, null);

        mockMvc.perform(get("/card")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_Cards));
    }

    @Test //pass
    public void checkGet_whenNoParam_thenAllCardsAreReturned2() throws Exception {
        when(cardRepository.findAll()).thenReturn(TestDataUtil.getTestCards());
        mockMvc.perform(get("/card")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_Cards));
    }

    @Test //pass
    public void checkGet_whenValidName_thenCardIsReturned() throws Exception {
        String cardName = "Name3";
        doReturn(getTestCards().subList(2, 3)).when(cardController).findCard(cardName, null);

        mockMvc.perform(get("/card")
                        .contentType("application/json")
                        .queryParam("name", cardName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(cardName));
    }

    @Test //pass
    public void checkGet_whenNotExistingName_thenNoCardsAreReturned() throws Exception {
        String cardName = "NotExistingCard";

        mockMvc.perform(get("/card")
                        .contentType("application/json")
                        .queryParam("name", cardName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test //pass
    public void checkGet_whenValidCost_thenCardIsReturned() throws Exception {
        String cardCost = "3";
        doReturn(getTestCards().subList(2, 3)).when(cardController).findCard(null, 3);

        mockMvc.perform(get("/card")
                        .contentType("application/json")
                        .queryParam("cost", cardCost))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cost").value(cardCost));
    }

    @Test //pass
    public void checkGet_whenValidNameAndCost_thenCardIsReturned() throws Exception {
        String cardName = "Name3";
        int cardCost = 3;
        doReturn(getTestCards().subList(2, 3)).when(cardController).findCard(cardName, cardCost);

        mockMvc.perform(get("/card")
                        .contentType("application/json")
                        .queryParam("name", cardName)
                        .queryParam("cost", String.valueOf(cardCost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(cardName));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(NoSuchElementException.class).when(cardController).findById("ID0");

        mockMvc.perform(get("/card/ID0")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenNewCard_thenIsOk() throws Exception {
        mockMvc.perform(post("/card")
                        .contentType("application/json")
                        .content("{\n" +
                                "\"id\": \"ID4\",\n" +
                                "\"name\": \"Name4\",\n" +
                                "\"cost\": 4,\n" +
                                "\"type\": \"Type4\",\n" +
                                "\"picture1\": \"Picture4\",\n" +
                                "\"picture2\": \"Picture4\",\n" +
                                "\"attack\": 4,\n" +
                                "\"health\": 4,\n" +
                                "\"description\": \"Description4\",\n" +
                                "\"flavorText\": \"Flavortext4\",\n" +
                                "\"artist\": \"Artist4\",\n" +
                                "\"levelUp\": \"LevelUp4\",\n" +
                                "\"spellSpeed\": \"SpellSpeed4\",\n" +
                                "\"cardSet\": \"CardSet4\",\n" +
                                "\"subType\": \"SubType4\",\n" +
                                "\"linkedRegions\": [],\n" +
                                "\"linkedKeywords\": []\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidCard_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(cardController).insert(new Card());

        mockMvc.perform(post("/card")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Region1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPut_whenNewCard_thenIsOk() throws Exception {
        mockMvc.perform(put("/card")
                        .contentType("application/json")
                        .content("{\n" +
                                "\"id\": \"ID3\",\n" +
                                "\"name\": \"Name4\",\n" +
                                "\"cost\": 4,\n" +
                                "\"type\": \"Type4\",\n" +
                                "\"picture1\": \"Picture4\",\n" +
                                "\"picture2\": \"Picture4\",\n" +
                                "\"attack\": 4,\n" +
                                "\"health\": 4,\n" +
                                "\"description\": \"Description4\",\n" +
                                "\"flavorText\": \"Flavortext4\",\n" +
                                "\"artist\": \"Artist4\",\n" +
                                "\"levelUp\": \"LevelUp4\",\n" +
                                "\"spellSpeed\": \"SpellSpeed4\",\n" +
                                "\"cardSet\": \"CardSet4\",\n" +
                                "\"subType\": \"SubType4\",\n" +
                                "\"linkedRegions\": [],\n" +
                                "\"linkedKeywords\": []\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPut_whenInvalidCard_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(cardController).update(new Card());

        mockMvc.perform(put("/card")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Card1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/card/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Mockito.verify(cardController).delete("1");
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(cardController).delete("99999");
        mockMvc.perform(delete("/card/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        Mockito.verify(cardController).delete("99999");
    }














//    @Test
//    public void checkGet_whenValidCostAndName_thenCardIsReturned() throws Exception {
//        String cardCost = "3";
//        String cardName = "Name3";
//        doReturn(getTestCards().subList(2, 3)).when(cardController).findCard(cardName, 3);
//
//        mockMvc.perform(get("/card")
//                        .contentType("application/json")
//                        .queryParam("cost", cardCost))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].cost").value(cardCost))
//                .andExpect(jsonPath("$[0].name").value(cardName));
//    }

}