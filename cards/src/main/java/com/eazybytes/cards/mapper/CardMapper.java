package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Cards;

public class CardMapper {
    public static void cardToCardDto(CardDto cardDto, Cards card) {
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setTotalLimit(card.getTotalLimit());
    }
}
