package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.models.Keyword;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = KeywordController.class)
@AutoConfigureMockMvc(addFilters = false)
class KeywordControllerTest {

    private static final String JSON_ALL_KEYWORDS = """
            [
              {
                "id": 1,
                "name": "Keyword1",
                "description": "Description1"
              },
              {
                "id": 2,
                "name": "Keyword2",
                "description": "Description2"
              },
              {
                "id": 3,
                "name": "Keyword3",
                "description": "Description3"
              },
              {
                "id": 4,
                "name": "Keyword4",
                "description": "Description4"
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeywordController keywordController;


    @Test //pass
    public void checkGet_whenNoParam_thenAllKeywordsAreReturned() throws Exception {
        doReturn(getTestKeywords()).when(keywordController).findByName(null);

        mockMvc.perform(get("/keyword")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_KEYWORDS));
    }

    @Test //pass
    public void checkGet_whenValidName_thenKeywordIsReturned() throws Exception {
        String regionName = "Region4";
        doReturn(getTestRegions().subList(3, 4)).when(keywordController).findByName(regionName);

        mockMvc.perform(get("/keyword")
                        .contentType("application/json")
                        .queryParam("name", regionName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(regionName));
    }

    @Test //pass
    public void checkGet_whenNotExistingName_thenNoKeywordsAreReturned() throws Exception {
        String regionName = "NotExistingRegion";

        mockMvc.perform(get("/keyword")
                        .contentType("application/json")
                        .queryParam("name", regionName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test //pass
    public void checkGetById_whenValidId_thenKeywordIsReturned() throws Exception {
        Keyword expected = getTestKeyword();
        doReturn(expected).when(keywordController).findById(1);

        mockMvc.perform(get("/keyword/" + 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Keyword1")));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(NoSuchElementException.class).when(keywordController).findById(0);

        mockMvc.perform(get("/keyword/" + 0)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test //pass
    public void checkPost_whenNewKeyword_thenIsOk() throws Exception {
        mockMvc.perform(post("/keyword")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": 0,\n" +
                                "  \"name\": \"string\",\n" +
                                "  \"description\": \"string\",\n" +
                                "  \"linkedCards\": []\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidItem_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(keywordController).insert(new Keyword());

        mockMvc.perform(post("/keyword")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Region1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkPut_whenValidKeyword_thenIsOk() throws Exception {
        mockMvc.perform(put("/keyword")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"string\",\n" +
                                "  \"description\": \"string\",\n" +
                                "  \"linkedCards\": []\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidKeyword_thenIsConflict() throws Exception {
        doThrow(ConstraintViolationException.class).when(keywordController).update(new Keyword());


        mockMvc.perform(put("/keyword")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"Keyword1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test //pass
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/keyword/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Mockito.verify(keywordController).delete(eq(1));
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(keywordController).delete(99999);
        mockMvc.perform(delete("/keyword/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        Mockito.verify(keywordController).delete(eq(99999));
    }


}