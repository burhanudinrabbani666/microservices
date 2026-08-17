package com.eazybytes.loans.repositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.loans.entity.Loans;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {

        /**
         * 
         * @param mobileNumber
         * @return
         */
        Optional<Loans> findByMobileNumber(String mobileNumber);
}
