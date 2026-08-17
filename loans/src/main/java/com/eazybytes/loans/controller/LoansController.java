package com.eazybytes.loans.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.loans.dto.CreateLoansDto;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.LoansSuccessResponseDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.service.ILoansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        @Operation(summary = "Get Loans", description = "REST API to get Loans details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping(path = "/{mobileNumber}")
        public ResponseEntity<LoansSuccessResponseDto> getLoansByMobileNumber(
                        @PathVariable @Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "LoanNumber must be 10 digits") @Schema(example = "0812345678") String mobileNumber) {

                Loans loans = this.loansService.getLoanByMobileNumber(mobileNumber);
                LoansSuccessResponseDto response = new LoansSuccessResponseDto();

                response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
                response.setStatusMessage("Success Get Loans");
                response.setData(loans);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * 
         * @param createLoansDto
         * @return
         */
        @Operation(summary = "Create Loans", description = "REST API to create Loans")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "CREATED"),
                        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "409", description = "CONFLICT", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PostMapping
        public ResponseEntity<LoansSuccessResponseDto> createLoans(@RequestBody @Valid CreateLoansDto createLoansDto) {
                Loans newLoans = this.loansService.createLoan(createLoansDto.getMobileNumber());
                LoansSuccessResponseDto response = new LoansSuccessResponseDto();

                response.setStatusCode(String.valueOf(HttpStatus.CREATED.value()));
                response.setStatusMessage("Success Create new Loans");
                response.setData(newLoans);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }

        /**
         * 
         * @param id
         * @param loansDto
         * @return
         */
        @Operation(summary = "Update Loans", description = "REST API to update Loans")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "NO CONTENT"),
                        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "409", description = "CONFLICT", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping(path = "/{id}")
        public ResponseEntity<String> updateLoans(@PathVariable @Valid Long id, @RequestBody @Valid LoansDto loansDto) {
                this.loansService.updateLoan(id, loansDto);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success update loans");
        }

        /**
         * 
         * @param id
         * @param loansDto
         * @return
         */
        @Operation(summary = "Delete Loans", description = "REST API to delete Loans")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "NO CONTENT"),
                        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping("/{mobileNumber}")
        public ResponseEntity<String> deleteLoans(
                        @PathVariable @Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "LoanNumber must be 10 digits") @Schema(example = "0812345678") String mobileNumber) {
                this.loansService.deleteLoanByMobileNumber(mobileNumber);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfulyy deleted");
        }

}
