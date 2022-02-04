package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.repositories.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static ch.bbcag.lor_springapi.utils.TestDataUtil.getTestRegions;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RegionController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegionControllerTest {

    private static final String JSON_ALL_REGIONS = """
            [
              {
                "id": 1,
                "name": "Region1",
                "icon": "icon1",
                "linkedCards": [
                    {
                        "id": "newId",
                        "name": "Card",
                        "cost": 2,
                        "attack": null,
                        "health": null,
                        "description": null,
                        "flavortext": null,
                        "type": "type",
                        "picture1": "pic1",
                        "picture2": "pic2",
                        "artist": null,
                        "levelUp": null,
                        "spellSpeed": null,
                        "cardSet": null,
                        "subType": null,
                        "rarity": {
                            "id": 5,
                            "name": "None",
                            "shardCost": 0,
                            "coinCost": 0,
                            "cards": []
                        }
                    }
                ]
              },
              {
                "id": 2,
                "name": "Region2",
                "icon": "icon2",
                "linkedCards": [
                    {
                        "id": "newId",
                        "name": "Card",
                        "cost": 2,
                        "attack": null,
                        "health": null,
                        "description": null,
                        "flavortext": null,
                        "type": "type",
                        "picture1": "pic1",
                        "picture2": "pic2",
                        "artist": null,
                        "levelUp": null,
                        "spellSpeed": null,
                        "cardSet": null,
                        "subType": null,
                        "rarity": {
                            "id": 5,
                            "name": "None",
                            "shardCost": 0,
                            "coinCost": 0,
                            "cards": []
                        }
                    }
                ]
              },
              {
                "id": 3,
                "name": "Region3",
                "icon": "icon3",
                "linkedCards": [
                    {
                        "id": "newId",
                        "name": "Card",
                        "cost": 2,
                        "attack": null,
                        "health": null,
                        "description": null,
                        "flavortext": null,
                        "type": "type",
                        "picture1": "pic1",
                        "picture2": "pic2",
                        "artist": null,
                        "levelUp": null,
                        "spellSpeed": null,
                        "cardSet": null,
                        "subType": null,
                        "rarity": {
                            "id": 5,
                            "name": "None",
                            "shardCost": 0,
                            "coinCost": 0,
                            "cards": []
                        }
                    }
                ]
              },
              {
                "id": 4,
                "name": "Region4",
                "icon": "icon4",
                "linkedCards": [
                    {
                        "id": "newId",
                        "name": "Card",
                        "cost": 2,
                        "attack": null,
                        "health": null,
                        "description": null,
                        "flavortext": null,
                        "type": "type",
                        "picture1": "pic1",
                        "picture2": "pic2",
                        "artist": null,
                        "levelUp": null,
                        "spellSpeed": null,
                        "cardSet": null,
                        "subType": null,
                        "rarity": {
                            "id": 5,
                            "name": "None",
                            "shardCost": 0,
                            "coinCost": 0,
                            "cards": []
                        }
                    }
                ]
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionRepository regionRepository;

    @Test
    public void checkGet_whenNoParam_thenAllRegionsAreReturned() throws Exception {
        doReturn(getTestRegions()).when(regionRepository).findAll();

        mockMvc.perform(get("/region")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_REGIONS));
    }

}