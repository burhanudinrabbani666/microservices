package com.eazybytes.cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.cards.constans.CardsConstants;
import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.dto.CreateCardDto;
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
        private final String CARD_ID_FIELD = "id";

        @Override
        public Cards getCardByMobileNumber(String mobileNumber) {
                Optional<Cards> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
                Cards card = this.checkIsCardPresent(optionalCard, MOBILE_NUMBER_FIELD, mobileNumber);
                return card;
        }

        @Override
        public Cards createCard(CreateCardDto createCardDto) {
                Optional<Cards> optionalCard = cardRepository.findByMobileNumber(createCardDto.getMobileNumber());
                this.checkIsCardAlreadyExist(optionalCard);

                Cards newCard = this.cardRepository.save(this.createNewCard(createCardDto.getMobileNumber()));
                return newCard;
        }

        @Override
        public void updateCard(Long id, CardDto cardDto) {
                Optional<Cards> existMobileNumber = this.cardRepository.findByMobileNumber(cardDto.getMobileNumber());
                this.checkIsCardAlreadyExist(existMobileNumber);

                Optional<Cards> optionalCard = this.cardRepository.findById(id);
                Cards card = this.checkIsCardPresent(optionalCard, CARD_ID_FIELD, String.valueOf(id));

                CardMapper.cardDtoToCard(card, cardDto);
                this.cardRepository.save(card);
        }

        @Override
        public void deleteCardByMobileNumber(String mobileNumber) {
                Optional<Cards> optionalCard = this.cardRepository.findByMobileNumber(mobileNumber);
                Cards card = this.checkIsCardPresent(optionalCard, MOBILE_NUMBER_FIELD, mobileNumber);

                this.cardRepository.deleteById(card.getCardId());
        }

        /**
         * @throws ResourceNotFoundException
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
