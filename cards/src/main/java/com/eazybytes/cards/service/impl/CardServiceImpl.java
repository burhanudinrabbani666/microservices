package com.eazybytes.cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.cards.constans.CardsConstants;
import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardMapper;
import com.eazybytes.cards.repositry.CardRepository;
import com.eazybytes.cards.service.ICardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    private final String MOBILE_NUMBER_FIELD = "mobileNumber";

    @Override
    public CardDto getCard(String mobileNumber) {
        Optional<Cards> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        Cards card = this.checkIsCardPresent(optionalCard, MOBILE_NUMBER_FIELD, mobileNumber);

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
    public void updateCard(CardDto cardDto) {
        Optional<Cards> optionalCard = this.cardRepository.findByMobileNumber(cardDto.getMobileNumber());
        Cards card = this.checkIsCardPresent(optionalCard, MOBILE_NUMBER_FIELD, cardDto.getMobileNumber());

        CardMapper.cardDtoToCard(card, cardDto);
        this.cardRepository.save(card);
    }

    @Override
    public void deleteCard(String mobileNumber) {
        Optional<Cards> optionalCard = this.cardRepository.findByMobileNumber(mobileNumber);
        Cards card = this.checkIsCardPresent(optionalCard, MOBILE_NUMBER_FIELD, mobileNumber);

        this.cardRepository.deleteById(card.getCardId());
    }

    /**
     * 
     * @param optionalCard
     * @return
     */
    private Cards checkIsCardPresent(Optional<Cards> optionalCard, String fieldName, String fieldValue) {
        return optionalCard.orElseThrow(() -> new ResourceNotFoundException("Card", fieldName, fieldValue));
    }

    /**
     * 
     * @param optionalCard
     */
    private void checkIsCardAlreadyExist(Optional<Cards> optionalCard) {
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistException("Card already exist");
        }
    }

    /**
     * 
     * @param mobileNumber
     * @return
     */
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
