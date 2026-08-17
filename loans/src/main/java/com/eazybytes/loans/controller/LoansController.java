package com.eazybytes.loans.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.loans.dto.LoansSuccessResponseDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.service.ILoansService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Loans", description = "CRUD REST APIs in EazyBank for loans details")
@Validated
@RequiredArgsConstructor
public class LoansController {
        private final ILoansService loansService;

        /**
         * 
         * @param mobileNumber
         * @return
         */
        @GetMapping(path = "/{mobileNumber}")
        public ResponseEntity<LoansSuccessResponseDto> getLoansByMobileNumber(
                        @PathVariable @Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "LoanNumber must be 10 digits") String mobileNumber) {

                Loans loans = this.loansService.getLoanByMobileNumber(mobileNumber);
                LoansSuccessResponseDto response = new LoansSuccessResponseDto();

                response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
                response.setStatusMessage("Success Get Loans");
                response.setData(loans);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

}
