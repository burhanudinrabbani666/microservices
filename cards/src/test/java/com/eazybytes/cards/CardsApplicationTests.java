package com.eazybytes.cards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.eazybytes.cards.dto.CreateCardDto;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CardsApplicationTests {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void successCreateUser() throws Exception {
                CreateCardDto createCardDto = new CreateCardDto();
                createCardDto.setMobileNumber("0812345678");

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createCardDto);

                mockMvc.perform(post("/api/cards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isCreated());
        }

        @Test
        void failedCreateUser() throws Exception {
                CreateCardDto createCardDto = new CreateCardDto();
                createCardDto.setMobileNumber("0812345");

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createCardDto);

                mockMvc.perform(post("/api/cards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isBadRequest());
        }

}
