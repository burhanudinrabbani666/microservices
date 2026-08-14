package com.eazybytes.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.eazybytes.accounts.entity.Accounts;

import jakarta.transaction.Transactional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    /**
     * 
     * @param customerId
     * @return
     */
    Optional<Accounts> findByCustomerId(Long customerId);

    /**
     * 
     * @param customerId
     */
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);

}