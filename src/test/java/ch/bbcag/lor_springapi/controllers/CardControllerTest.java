package ch.bbcag.lor_springapi.controllers;

import ch.bbcag.lor_springapi.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CardControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CardRepository cardRepository;
//
//    @Test
//    public void checkGet_whenNoParam_thenAllCardsAreReturned() throws Exception {
//        doReturn(getTestCards()).when(cardRepository).findAll();
//
//        mockMvc.perform(get("/tags")
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(JSON_ALL_TAGS));
//    }
}