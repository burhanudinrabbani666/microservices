package com.eazybytes.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.eazybytes.cards.dto.CreateCardDto;
import com.eazybytes.cards.repositry.CardRepository;
import com.eazybytes.cards.service.ICardService;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
class CardsApplicationTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ICardService cardService;

        @Autowired
        private CardRepository cardRepository;

        private String statusCode;
        private String statusMessage;

        private final String statusCodeField = "$.statusCode";
        private final String statusMessageField = "$.statusMessage";
        private final String mockMobileNumber = "0818765432";

        @BeforeEach
        void setup() {
                this.cardRepository.deleteAll();

                // Create test Card
                CreateCardDto newCard = new CreateCardDto();
                newCard.setMobileNumber(this.mockMobileNumber);
                this.cardService.createCard(newCard);
        }

        /**
         * =================================================================================================
         * GET Endpoints
         * =================================================================================================
         */

        @Test
        void successGetCard() throws Exception {
                this.statusCode = String.valueOf(HttpStatus.OK.value());
                this.statusMessage = "Success get Card Details";

                mockMvc.perform(get("/api/cards/" + this.mockMobileNumber))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(this.statusCodeField).value(this.statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(this.statusMessage))
                                .andExpect(jsonPath("$.card.mobileNumber").value(this.mockMobileNumber));
        }

        @Test
        void cardNotFound() throws Exception {
                this.statusCode = String.valueOf(HttpStatus.NOT_FOUND.value());
                this.statusMessage = HttpStatus.NOT_FOUND.getReasonPhrase();

                Long invalidMobileNumber = 9999999999L;
                String errors = "Card not found with given input data mobileNumber: " + invalidMobileNumber;

                mockMvc.perform(get("/api/cards/" + invalidMobileNumber))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath(this.statusCodeField).value(this.statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(this.statusMessage))
                                .andExpect(jsonPath("$.errors").value(errors));
        }

        @Test
        void mobileNumberToLong() throws Exception {
                String statusCode = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
                String statusMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

                mockMvc.perform(get("/api/cards/" + 99999999991L))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath(this.statusCodeField).value(statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(statusMessage));
        }

        /**
         * =================================================================================================
         * POST Endpoints
         * =================================================================================================
         */

        @Test
        void successCreateUser() throws Exception {
                CreateCardDto createCardDto = new CreateCardDto();
                createCardDto.setMobileNumber("0812345678");

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createCardDto);

                this.statusCode = String.valueOf(HttpStatus.CREATED.value());
                this.statusMessage = "Success create Card Details";

                mockMvc.perform(post("/api/cards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath(this.statusCodeField).value(this.statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        @Test
        void failedCreateUserValidationError() throws Exception {
                CreateCardDto createCardDto = new CreateCardDto();
                createCardDto.setMobileNumber("");

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createCardDto);

                this.statusCode = String.valueOf(HttpStatus.BAD_REQUEST.value());
                this.statusMessage = HttpStatus.BAD_REQUEST.getReasonPhrase();

                mockMvc.perform(post("/api/cards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath(this.statusCodeField).value(this.statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        @Test
        void failedCreateUserConflict() throws Exception {
                CreateCardDto createCardDto = new CreateCardDto();
                createCardDto.setMobileNumber(this.mockMobileNumber);

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createCardDto);

                this.statusCode = String.valueOf(HttpStatus.CONFLICT.value());
                this.statusMessage = HttpStatus.CONFLICT.getReasonPhrase();

                mockMvc.perform(post("/api/cards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isConflict())
                                .andExpect(jsonPath(this.statusCodeField).value(this.statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        /**
         * =================================================================================================
         * PUT Endpoints
         * =================================================================================================
         */
        // TODO: create update test endpoint

        /**
         * =================================================================================================
         * DELETE Endpoints
         * =================================================================================================
         */
        @Test
        void successDeleteCard() throws Exception {
                mockMvc.perform(delete("/api/cards/" + this.mockMobileNumber)).andExpect(status().isNoContent());
        }

        @Test
        void failedDeleteNotFound() throws Exception {
                this.statusCode = String.valueOf(HttpStatus.NOT_FOUND.value());
                this.statusMessage = HttpStatus.NOT_FOUND.getReasonPhrase();

                Long invalidMobileNumber = 9999999999L;
                String errors = "Card not found with given input data mobileNumber: " + invalidMobileNumber;

                mockMvc.perform(delete("/api/cards/" + invalidMobileNumber))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath(this.statusCodeField).value(this.statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(this.statusMessage))
                                .andExpect(jsonPath("$.errors").value(errors));
        }

        @Test
        void failedDeleteMobileNumberToLong() throws Exception {
                String statusCode = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
                String statusMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

                mockMvc.perform(get("/api/cards/" + 99999999991L))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath(this.statusCodeField).value(statusCode))
                                .andExpect(jsonPath(this.statusMessageField).value(statusMessage));
        }

}
