package ch.bbcag.lor_springapi.controllers;

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

import static ch.bbcag.lor_springapi.utils.TestDataUtil.getTestRegion;
import static ch.bbcag.lor_springapi.utils.TestDataUtil.getTestRegions;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegionController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegionControllerTest {

    private static final String JSON_ALL_REGIONS = """
            [
              {
                "id": 1,
                "name": "Region1",
                "icon": "Icon1"
              },
              {
                "id": 2,
                "name": "Region2",
                "icon": "Icon2"
              },
              {
                "id": 3,
                "name": "Region3",
                "icon": "Icon3"
              },
              {
                "id": 4,
                "name": "Region4",
                "icon": "Icon4"
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionController regionController;


    @Test //pass
    public void checkGet_whenNoParam_thenAllRegionsAreReturned() throws Exception {
        doReturn(getTestRegions()).when(regionController).findByName(null);

        mockMvc.perform(get("/region")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_REGIONS));
    }

    @Test //pass
    public void checkGet_whenValidName_thenRegionIsReturned() throws Exception {
        String regionName = "Region4";
        doReturn(getTestRegions().subList(3, 4)).when(regionController).findByName(regionName);

        mockMvc.perform(get("/region")
                        .contentType("application/json")
                        .queryParam("name", regionName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(regionName));
    }

    @Test //pass
    public void checkGet_whenNotExistingName_thenNoRegionsAreReturned() throws Exception {
        String regionName = "NotExistingRegion";

        mockMvc.perform(get("/region")
                        .contentType("application/json")
                        .queryParam("name", regionName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test //pass
    public void checkGetById_whenValidId_thenRegionIsReturned() throws Exception {
        Region expected = getTestRegion();
        doReturn(expected).when(regionController).findById(1);

        mockMvc.perform(get("/region/" + 1)
               .contentType("application/json"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("Region1")));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(NoSuchElementException.class).when(regionController).findById(0);

        mockMvc.perform(get("/region/" + 0)
               .contentType("application/json"))
               .andExpect(status().isNotFound());
    }

    @Test //pass
    public void checkPost_whenNewRegion_thenIsOk() throws Exception {
        mockMvc.perform(post("/region")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": 0,\n" +
                                "  \"name\": \"string\",\n" +
                                "  \"icon\": \"string\",\n" +
                                "  \"linkedCards\": []\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidItem_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(regionController).insert(new Region());

        mockMvc.perform(post("/region")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Region1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkPut_whenValidRegion_thenIsOk() throws Exception {
        mockMvc.perform(put("/region")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"newRegion\",\n" +
                                "  \"icon\": \"newIcon\",\n" +
                                "  \"linkedCards\": []\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidRegion_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(regionController).update(new Region());


        mockMvc.perform(put("/region")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Region1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/region/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Mockito.verify(regionController).deleteById(eq(1));
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(regionController).deleteById(99999);
        mockMvc.perform(delete("/region/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        Mockito.verify(regionController).deleteById(eq(99999));
    }

}