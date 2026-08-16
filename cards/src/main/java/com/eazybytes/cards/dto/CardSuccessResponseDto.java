package com.eazybytes.cards.dto;

import com.eazybytes.cards.entity.Cards;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Success Response", description = "Schema to hold success response information")
public class CardSuccessResponseDto extends ResponseDto {
        private Cards card;
}
