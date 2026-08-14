package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Card;

public interface ICardService {
    /**
     * 
     * @param mobileNumber
     * @return
     */
    Card getCard(String mobileNumber);

    /**
     * @param cardDto
     * @return
     */
    Card createCard(CardDto cardDto);

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
