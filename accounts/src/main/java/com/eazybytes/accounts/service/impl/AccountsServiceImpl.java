package com.eazybytes.accounts.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.constants.CustomerConstant;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> existCustomer = this.customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (existCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(CustomerConstant.MOBILE_NUMBER_ALREADY_EXIST
                    + customerDto.getMobileNumber());
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = this.customerRepository.save(customer);
        this.accountsRepository.save(this.createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        long randomAccNumber = AccountsConstants.baseRandomNumber;
        Accounts newAccount = new Accounts();

        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

}
