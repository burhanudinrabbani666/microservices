package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * 
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    /**
     * 
     * @param mobileNumber
     * @return Account details based on a given number
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * 
     * @param customerDto
     * @return boolean indicating if the update of account details is successfully
     *         or not
     */
    boolean updatedAccount(CustomerDto customerDto);

}
