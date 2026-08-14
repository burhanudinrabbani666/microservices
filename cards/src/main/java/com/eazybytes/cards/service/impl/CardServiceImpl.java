package com.eazybytes.cards.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Card;
import com.eazybytes.cards.repositry.CardRepository;
import com.eazybytes.cards.service.ICardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    @Override
    public Card getCard(String mobileNumber) {
        Optional<Card> optionalCard = this.cardRepository.findByMobileNumber(mobileNumber);
        Card card = this.checkIsCardPresent(optionalCard);
        return card;
    }

    @Override
    public Card createCard(CardDto cardDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCard'");
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCard'");
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCard'");
    }

    private Card checkIsCardPresent(Optional<Card> optionalCard) {
        return optionalCard.orElseThrow(() -> new RuntimeException("Card not found"));
    }

}
