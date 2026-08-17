package com.eazybytes.loans.repositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.loans.entity.Loans;

public interface LoansRepository extends JpaRepository<Loans, Long> {

        /**
         * 
         * @param mobileNumber
         * @return
         */
        Optional<Loans> findByMobileNumber(String mobileNumber);
}
