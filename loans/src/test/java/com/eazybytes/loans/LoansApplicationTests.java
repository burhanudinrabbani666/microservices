package com.eazybytes.loans;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.eazybytes.loans.dto.CreateLoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.repositoy.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
class LoansApplicationTests {

        @Autowired
        private ILoansService loansService;

        @Autowired
        private LoansRepository loansRepository;

        @Autowired
        private MockMvc mockMvc;

        private Loans loans;
        private String statusCode;
        private String statusMessage;

        private final String statusCodeField = "$.statusCode";
        private final String statusMessageField = "$.statusMessage";
        private final String mockMobileNumber = "0812345678";
        private final String mockMobileNumberPost = "0812345679";
        private final String mockMobileNumbernotFound = "9999999999";
        private final String apiPath = "/api/loans";

        private final String statusCodeOk = String.valueOf(HttpStatus.OK.value());
        private final String statusCodeCreated = String.valueOf(HttpStatus.CREATED.value());
        private final String statusCodeNotFound = String.valueOf(HttpStatus.NOT_FOUND.value());
        private final String statusCodeBadRequest = String.valueOf(HttpStatus.BAD_REQUEST.value());
        private final String statusCodeConflict = String.valueOf(HttpStatus.CONFLICT.value());
        private final String statusCodeInternalServerError = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

        private final String reasonPhraseBadRequest = HttpStatus.BAD_REQUEST.getReasonPhrase();
        private final String reasonPhraseNotFound = HttpStatus.NOT_FOUND.getReasonPhrase();
        private final String reasonPhraseConflict = HttpStatus.CONFLICT.getReasonPhrase();
        private final String reasonPhraseInternalServerError = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

        @BeforeEach
        void setupAndCleaning() {
                this.loansRepository.deleteAll();
                this.loans = this.loansService.createLoan(mockMobileNumber);
        }

        // ===============================================================
        // GET endpoints
        // ===============================================================

        @Test
        void successGetLoans() throws Exception {
                this.statusCode = this.statusCodeOk;
                this.statusMessage = "Success Get Loans";

                ResultActions response = this.mockMvc.perform(get(apiPath + "/" + mockMobileNumber));

                response.andExpect(status().isOk());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        @Test
        void failedGetLoansNotFound() throws Exception {
                this.statusCode = this.statusCodeNotFound;
                this.statusMessage = this.reasonPhraseNotFound;

                ResultActions response = this.mockMvc.perform(get(apiPath + "/" + mockMobileNumbernotFound));

                response.andExpect(status().isNotFound());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        @Test
        void failedGetLoansBadRequest() throws Exception {
                this.statusCode = this.statusCodeInternalServerError;
                this.statusMessage = this.reasonPhraseInternalServerError;

                ResultActions response = this.mockMvc.perform(get(apiPath + "/" + "1111"));

                response.andExpect(status().isInternalServerError());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        // ===============================================================
        // POST endpoints
        // ===============================================================

        @Test
        void successCreateLoans() throws Exception {
                this.statusCode = this.statusCodeCreated;
                this.statusMessage = "Success Create new Loans";

                CreateLoansDto createLoansDto = new CreateLoansDto();
                createLoansDto.setMobileNumber(this.mockMobileNumberPost);

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createLoansDto);

                ResultActions response = this.mockMvc.perform(post(apiPath)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody));

                response.andExpect(status().isCreated());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));

        }

        @Test
        void failedCreateLoansBadRequest() throws Exception {
                this.statusCode = this.statusCodeBadRequest;
                this.statusMessage = this.reasonPhraseBadRequest;

                CreateLoansDto createLoansDto = new CreateLoansDto();
                createLoansDto.setMobileNumber(this.mockMobileNumberPost + 1);

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createLoansDto);

                ResultActions response = this.mockMvc.perform(post(apiPath)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody));

                response.andExpect(status().isBadRequest());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));

        }

        @Test
        void failedCreateLoansConflict() throws Exception {
                this.statusCode = this.statusCodeConflict;
                this.statusMessage = this.reasonPhraseConflict;

                CreateLoansDto createLoansDto = new CreateLoansDto();
                createLoansDto.setMobileNumber(this.mockMobileNumber); // mock of @BeforeEach

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(createLoansDto);

                ResultActions response = this.mockMvc.perform(post(apiPath)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody));

                response.andExpect(status().isConflict());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        // ===============================================================
        // PUT endpoints
        // ===============================================================

        // ===============================================================
        // DELETE endpoints
        // ===============================================================

}
