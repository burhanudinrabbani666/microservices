package com.eazybytes.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;

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
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts", description = "CRUD REST APIs in EazyBank for Accounts details")
@RequiredArgsConstructor
@Validated
public class AccountsControlller {

    private final IAccountsService accountsService;

    /**
     * 
     * @param customerDto
     * @return
     */
    @Operation(summary = "Create Account & Customer", description = "REST API to create new Customer &  Account inside EazyBank")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
        this.accountsService.createAccount(customerDto);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(AccountsConstants.STATUS_201);
        responseDto.setStatusMessage(AccountsConstants.MESSAGE_201);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * 
     * @param mobileNumber
     * @return
     */
    @Operation(summary = "Get Account & Customer", description = "REST API to fetch Customer &  Account details based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @RequestParam @Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") @Schema(example = "0812345678") String mobileNumber) {
        CustomerDto customerDto = this.accountsService.fetchAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    /**
     * 
     * @param customerDto
     * @return
     */
    @Operation(summary = "Update Account & Customer", description = "REST API to update Customer &  Account details based on a account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody @Valid CustomerDto customerDto) {
        boolean isUpdated = this.accountsService.updatedAccount(customerDto);
        HttpStatus httpStatusCode = isUpdated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseDto responseDto = new ResponseDto();

        responseDto.setStatusCode(isUpdated ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_500);
        responseDto.setStatusMessage(isUpdated ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_500);

        return ResponseEntity.status(httpStatusCode).body(responseDto);
    }

    /**
     * 
     * @param mobileNumber
     * @return
     */
    @Operation(summary = "Delete Account & Customer", description = "REST API to delete Customer &  Account details based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @RequestParam @Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") @Schema(example = "0812345678") String mobileNumber) {
        boolean isDeleted = this.accountsService.deleteAccount(mobileNumber);
        HttpStatus httpStatusCode = isDeleted ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        ResponseDto responseDto = new ResponseDto();

        responseDto.setStatusCode(isDeleted ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_417);
        responseDto.setStatusMessage(isDeleted ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_417_DELETE);

        return ResponseEntity.status(httpStatusCode).body(responseDto);
    }

}
