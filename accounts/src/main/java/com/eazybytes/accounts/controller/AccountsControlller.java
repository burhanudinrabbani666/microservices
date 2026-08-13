package com.eazybytes.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountsControlller {

    private final IAccountsService accountsService;

    /**
     * 
     * @param customerDto
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
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
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDto customerDto = this.accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = this.accountsService.updatedAccount(customerDto);
        HttpStatus httpStatusCode = isUpdated ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseDto responseDto = new ResponseDto();

        responseDto.setStatusCode(isUpdated ? AccountsConstants.STATUS_200 : AccountsConstants.STATUS_500);
        responseDto.setStatusMessage(isUpdated ? AccountsConstants.MESSAGE_200 : AccountsConstants.MESSAGE_500);

        return ResponseEntity.status(httpStatusCode).body(responseDto);
    }

}
