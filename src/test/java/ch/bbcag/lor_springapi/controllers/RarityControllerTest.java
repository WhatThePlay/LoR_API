package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Rarity;
import ch.bbcag.lor_springapi.models.Region;
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

import static ch.bbcag.lor_springapi.utils.TestDataUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RarityController.class)
@AutoConfigureMockMvc(addFilters = false)
class RarityControllerTest {

    private static final String JSON_ALL_RARITIES = """
            [
              {
                "id": 1,
                "name": "Rarity1",
                "shardCost": 1,
                "coinCost": 1
              },
              {
                "id": 2,
                "name": "Rarity2",
                "shardCost": 2,
                "coinCost": 2
              },
              {
                "id": 3,
                "name": "Rarity3",
                "shardCost": 3,
                "coinCost": 3
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RarityController rarityController;

    @Test //pass
    public void checkGet_whenNoParam_thenAllRaritiesAreReturned() throws Exception {
        doReturn(getTestRarities()).when(rarityController).findByName(null);

        mockMvc.perform(get("/rarity")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_RARITIES));
    }

    @Test //pass
    public void checkGet_whenValidName_thenRarityIsReturned() throws Exception {
        String rarityName = "Rarity3";
        doReturn(getTestRarities().subList(2, 3)).when(rarityController).findByName(rarityName);

        mockMvc.perform(get("/rarity")
                        .contentType("application/json")
                        .queryParam("name", rarityName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(rarityName));
    }

    @Test //pass
    public void checkGet_whenNotExistingName_thenNoRaritiesAreReturned() throws Exception {
        String rarityName = "NotExistingRarity";

        mockMvc.perform(get("/rarity")
                        .contentType("application/json")
                        .queryParam("name", rarityName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test //pass
    public void checkGetById_whenValidId_thenRarityIsReturned() throws Exception {
        Rarity expected = getTestRarity();
        doReturn(expected).when(rarityController).findById(1);

        mockMvc.perform(get("/rarity/" + 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Rarity1")));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(NoSuchElementException.class).when(rarityController).findById(0);

        mockMvc.perform(get("/rarity/" + 0)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test //pass
    public void checkPost_whenNewRarity_thenIsOk() throws Exception {
        mockMvc.perform(post("/rarity")
                        .contentType("application/json")
                        .content("{\n" +
                                "\"id\": 4,\n" +
                                "\"name\": \"Rarity4\",\n" +
                                "\"shardCost\": 4,\n" +
                                "\"coinCost\": 4\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidRarity_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(rarityController).insert(new Rarity());

        mockMvc.perform(post("/rarity")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Region1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkPut_whenValidRarity_thenIsOk() throws Exception {
        mockMvc.perform(put("/rarity")
                        .contentType("application/json")
                        .content("{\n" +
                                "\"id\": 3,\n" +
                                "\"name\": \"Rarity3\",\n" +
                                "\"shardCost\": 3,\n" +
                                "\"coinCost\": 4\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidRarity_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(rarityController).update(new Rarity());


        mockMvc.perform(put("/rarity")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Rarity1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/rarity/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Mockito.verify(rarityController).delete(eq(1));
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(rarityController).delete(99999);
        mockMvc.perform(delete("/rarity/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        Mockito.verify(rarityController).delete(eq(99999));
    }
}