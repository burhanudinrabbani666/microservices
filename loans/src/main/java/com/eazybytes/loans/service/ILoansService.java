package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;

public interface ILoansService {
        /**
         *
         * @param mobileNumber - Mobile Number of the Customer
         * @return newLoans
         */
        Loans createLoan(String mobileNumber);

        /**
         *
         * @param mobileNumber - Input mobile Number
         * @return Loan Details based on a given mobileNumber
         */
        Loans getLoanByMobileNumber(String mobileNumber);

        /**
         *
         * @param loansDto - LoansDto Object
         * @return
         */
        void updateLoan(Long id, LoansDto loansDto);

        /**
         *
         * @param mobileNumber - Input Mobile Number
         * @return
         */
        void deleteLoanByMobileNumber(String mobileNumber);
}
