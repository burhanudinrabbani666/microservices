package com.eazybytes.cards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.dto.CardSuccessResponseDto;
import com.eazybytes.cards.dto.CreateCardDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.service.ICardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping(path = "/api/cards")
@RequiredArgsConstructor
@Validated
public class CardController {

        private final ICardService cardService;

        /**
         * 
         * @param mobiileNumber
         * @return
         */
        @GetMapping(path = "/{mobileNumber}")
        public ResponseEntity<CardSuccessResponseDto> getCardByMobileNumber(@PathVariable String mobileNumber) {
                Cards card = this.cardService.getCardByMobileNumber(mobileNumber);
                CardSuccessResponseDto response = new CardSuccessResponseDto();

                response.setStatusCode(HttpStatus.OK.toString());
                response.setStatusMessage("Success get Card Details");
                response.setCard(card);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * 
         * @param mobileNumber
         * @return
         */
        @PostMapping
        public ResponseEntity<CardSuccessResponseDto> createCard(@RequestBody @Valid CreateCardDto createCardDto) {
                Cards card = this.cardService.createCard(createCardDto);
                CardSuccessResponseDto response = new CardSuccessResponseDto();

                response.setStatusCode(HttpStatus.CREATED.toString());
                response.setStatusMessage("Success create Card Details");
                response.setCard(card);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        /**
         * 
         * @param mobileNumber
         * @return
         */
        @DeleteMapping(path = "/{mobileNumber}")
        public ResponseEntity<String> deleteCardByMobileNumber(@PathVariable String mobileNumber) {
                this.cardService.deleteCardByMobileNumber(mobileNumber);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card successfully Deleted");
        }
}
