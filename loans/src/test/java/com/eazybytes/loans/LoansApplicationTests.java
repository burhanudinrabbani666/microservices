package com.eazybytes.loans;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.repositoy.LoansRepository;
import com.eazybytes.loans.service.ILoansService;

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
        private final String mockMobileNumbernotFound = "9999999999";
        private final String apiPath = "/api/loans/";

        private final String statusCodeOk = String.valueOf(HttpStatus.OK.value());
        private final String statusCodeNotFound = String.valueOf(HttpStatus.NOT_FOUND.value());
        private final String statusCodeBadRequest = String.valueOf(HttpStatus.BAD_REQUEST.value());
        private final String statusCodeInternalServerError = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

        private final String reasonPhraseBadRequest = HttpStatus.BAD_REQUEST.getReasonPhrase();
        private final String reasonPhraseNotFound = HttpStatus.NOT_FOUND.getReasonPhrase();
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

                ResultActions response = this.mockMvc.perform(get(apiPath + mockMobileNumber));

                response.andExpect(status().isOk());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        @Test
        void failedGetLoansNotFound() throws Exception {
                this.statusCode = this.statusCodeNotFound;
                this.statusMessage = this.reasonPhraseNotFound;

                ResultActions response = this.mockMvc.perform(get(apiPath + mockMobileNumbernotFound));

                response.andExpect(status().isNotFound());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

        @Test
        void failedGetLoansBadRequest() throws Exception {
                this.statusCode = this.statusCodeInternalServerError;
                this.statusMessage = this.reasonPhraseInternalServerError;

                ResultActions response = this.mockMvc.perform(get(apiPath + "1111"));

                response.andExpect(status().isInternalServerError());
                response.andExpect(jsonPath(this.statusCodeField).value(this.statusCode));
                response.andExpect(jsonPath(this.statusMessageField).value(this.statusMessage));
        }

}
