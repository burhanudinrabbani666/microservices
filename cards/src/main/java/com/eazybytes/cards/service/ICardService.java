package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Cards;

public interface ICardService {
    /**
     * 
     * @param mobileNumber
     * @return
     */
    CardDto getCard(String mobileNumber);

    /**
     * @param cardDto
     * @return
     */
    CardDto createCard(String mobileNumber);

    /**
     * 
     * @param cardDto
     * @return
     */
    boolean updateCard(CardDto cardDto);

    /**
     * 
     * @param mobileNumber
     * @return
     */
    boolean deleteCard(String mobileNumber);

}
