package com.eazybytes.cards.dto;

import com.eazybytes.cards.entity.Cards;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardSuccessResponseDto extends ResponseDto {
        private Cards card;
}
