package com.eazybytes.cards.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eazybytes.cards.entity.Card;

/**
 * CardRepository
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * 
     * @param mobileNumber
     * @return
     */
    Optional<Card> findByMobileNumber(String mobileNumber);
}