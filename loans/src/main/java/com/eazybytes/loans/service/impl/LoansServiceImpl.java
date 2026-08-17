package com.eazybytes.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repositoy.LoansRepository;
import com.eazybytes.loans.service.ILoansService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements ILoansService {

        private final LoansRepository loansRepository;

        @Override
        public Loans createLoan(String mobileNumber) {
                Optional<Loans> optionalLoans = this.loansRepository.findByMobileNumber(mobileNumber);
                this.checkLoansIsAlreadyCreated(optionalLoans);

                Loans newLoans = this.createNewLoan(mobileNumber);
                Loans createdLoans = this.loansRepository.save(newLoans);

                return createdLoans;
        }

        @Override
        public Loans getLoanByMobileNumber(String mobileNumber) {
                Optional<Loans> optionalLoans = this.loansRepository.findByMobileNumber(mobileNumber);
                Loans loans = this.checkIfLoansIsPresent(optionalLoans, "mobileNumber", mobileNumber);
                return loans;
        }

        @Override
        public void updateLoan(Long id, LoansDto loansDto) {
                Optional<Loans> existLoans = this.loansRepository.findByMobileNumber(loansDto.getMobileNumber());
                this.checkLoansIsAlreadyCreated(existLoans);

                Optional<Loans> optionalLoans = this.loansRepository.findById(id);
                Loans loans = this.checkIfLoansIsPresent(optionalLoans, "loansId", id.toString());

                LoansMapper.mapToLoans(loansDto, loans);
                this.loansRepository.save(loans);
        }

        @Override
        public void deleteLoanByMobileNumber(String mobileNumber) {
                Optional<Loans> optionalLoans = this.loansRepository.findByMobileNumber(mobileNumber);
                Loans loans = this.checkIfLoansIsPresent(optionalLoans, "mobileNumber", mobileNumber);
                this.loansRepository.deleteById(loans.getLoanId());
        }

        /**
         * 
         * @param mobileNumber
         * @return
         */
        private Loans createNewLoan(String mobileNumber) {
                Loans newLoan = new Loans();
                long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);

                newLoan.setLoanNumber(Long.toString(randomLoanNumber));
                newLoan.setMobileNumber(mobileNumber);
                newLoan.setLoanType(LoansConstants.HOME_LOAN);
                newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
                newLoan.setAmountPaid(0);
                newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

                return newLoan;
        }

        /**
         * 
         * @param optionalLoans
         */
        private void checkLoansIsAlreadyCreated(Optional<Loans> optionalLoans) {
                if (optionalLoans.isPresent()) {
                        throw new LoanAlreadyExistsException("Loans with already exist");
                }
        }

        private Loans checkIfLoansIsPresent(Optional<Loans> optionalLoans, String fieldName, String filedValue) {
                return optionalLoans.orElseThrow(() -> new ResourceNotFoundException("Loans", fieldName, filedValue));
        }

}
