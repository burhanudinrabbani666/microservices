package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.dto.CreateCardDto;
import com.eazybytes.cards.entity.Cards;

public interface ICardService {
        /**
         * 
         * @param mobileNumber the unique identifier of the card
         * @return card details of given mobileNumber
         */
        Cards getCardByMobileNumber(String mobileNumber);

        /**
         * @param cardDto
         * @return card details of successful creating card process
         */
        Cards createCard(CreateCardDto createCardDto);

        /**
         * 
         * @param cardDto
         * @return
         */
        void updateCard(Long id, CardDto cardDto);

        /**
         * 
         * @param mobileNumber
         * @return
         */
        void deleteCardByMobileNumber(String mobileNumber);

}
