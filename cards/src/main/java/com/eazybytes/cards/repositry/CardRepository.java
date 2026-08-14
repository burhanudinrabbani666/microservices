package com.eazybytes.cards.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.cards.entity.Cards;

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
}