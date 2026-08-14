package com.eazybytes.cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.cards.constans.CardsConstants;
import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.mapper.CardMapper;
import com.eazybytes.cards.repositry.CardRepository;
import com.eazybytes.cards.service.ICardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    @Override
    public CardDto getCard(String mobileNumber) {
        Optional<Cards> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        Cards card = this.checkIsCardPresent(optionalCard);

        CardDto cardDto = new CardDto();
        CardMapper.cardToCardDto(cardDto, card);

        return cardDto;
    }

    @Override
    public CardDto createCard(String mobileNumber) {
        Optional<Cards> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        this.checkIsCardAlreadyExist(optionalCard);

        Cards newCard = this.cardRepository.save(this.createNewCard(mobileNumber));
        CardDto cardDto = new CardDto();

        CardMapper.cardToCardDto(cardDto, newCard);
        return cardDto;
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

    private Cards checkIsCardPresent(Optional<Cards> optionalCard) {
        return optionalCard.orElseThrow(() -> new RuntimeException("Cards not found"));
    }

    private void checkIsCardAlreadyExist(Optional<Cards> optionalCard) {
        if (optionalCard.isPresent()) {
            throw new RuntimeException("");
        }
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
