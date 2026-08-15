package com.eazybytes.cards.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.eazybytes.cards.entity.Cards;

import jakarta.transaction.Transactional;

/**
 * CardRepository
 */
public interface CardRepository extends JpaRepository<Cards, Long> {

    /**
     * 
     * @param mobileNumber
     * @return
     */
    Optional<Cards> findByMobileNumber(String mobileNumber);

    /**
     * 
     * @param mobileNumber
     */
    @Transactional
    @Modifying
    void deleteByMobileNumber(String mobileNumber);
}