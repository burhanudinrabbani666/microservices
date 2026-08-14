package com.eazybytes.accounts.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
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

    private final String MOBILE_NUMBER_FIELD = "mobileNumber";
    private final String CUSTOMER_ID_FIELD = "customerId";
    private final String ACCOUNT_NUMBER_FIELD = "accountNumber";

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        this.checkCustomerAlreadyExist(optionalCustomer);

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = this.customerRepository.save(customer);
        this.accountsRepository.save(this.createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(mobileNumber);
        Customer customer = this.checkCustomerIsPresent(optionalCustomer, MOBILE_NUMBER_FIELD, mobileNumber);

        Optional<Accounts> optionalAccounts = this.accountsRepository.findByCustomerId(customer.getCustomerId());
        String customerIdAString = String.valueOf(customer.getCustomerId());
        Accounts accounts = this.checkAccountIsPresent(optionalAccounts, CUSTOMER_ID_FIELD, customerIdAString);

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updatedAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            /// Update Account Logic
            Optional<Accounts> optionalAccounts = this.accountsRepository.findById(accountsDto.getAccountNumber());
            String accountNumberString = String.valueOf(accountsDto.getAccountNumber());
            Accounts accounts = this.checkAccountIsPresent(optionalAccounts, ACCOUNT_NUMBER_FIELD, accountNumberString);

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = this.accountsRepository.save(accounts);

            /// Update Customer Logic
            Long customerId = accounts.getCustomerId();
            Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
            Customer customer = this.checkCustomerIsPresent(optionalCustomer, CUSTOMER_ID_FIELD,
                    String.valueOf(customerId));

            CustomerMapper.mapToCustomer(customerDto, customer);
            this.customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * 
     * @param customer
     * @return new Account object for saved to database
     */
    private Accounts createNewAccount(Customer customer) {
        long randomAccNumber = AccountsConstants.baseRandomNumber;
        Accounts newAccount = new Accounts();

        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(mobileNumber);
        Customer customer = this.checkCustomerIsPresent(optionalCustomer, MOBILE_NUMBER_FIELD, mobileNumber);

        this.accountsRepository.deleteByCustomerId(customer.getCustomerId());
        this.customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    private Customer checkCustomerIsPresent(Optional<Customer> customer, String fieldName, String fieldValue) {
        return customer.orElseThrow(() -> new ResourceNotFoundException("Customer", fieldName, fieldValue));
    }

    private Accounts checkAccountIsPresent(Optional<Accounts> account, String fieldName, String fieldValue) {
        return account.orElseThrow(() -> new ResourceNotFoundException("Account", fieldName, fieldValue));
    }

    private void checkCustomerAlreadyExist(Optional<Customer> customer) {
        if (customer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer Already Exist");
        }
    }

}
