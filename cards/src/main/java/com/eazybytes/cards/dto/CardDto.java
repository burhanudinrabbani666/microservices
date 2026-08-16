package com.eazybytes.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(name = "Card Schema", description = "Schema to hold Card information")
public class CardDto {

        @Schema(description = "Mobile number of EazyBank Card", example = "0812345678")
        @NotEmpty(message = "mobileNumber cannot be empty or null")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        private String mobileNumber;

        @Schema(description = "Card number of EazyBank Card", example = "112233445566")
        @NotEmpty(message = "cardNumber cannot be empty or null")
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Cards number must be 12 digits")
        private String cardNumber;

        @Schema(description = "Card Type of EazyBank Card", example = "savings")
        @NotEmpty(message = "cardType cannot be empty or null")
        private String cardType;

        @Schema(description = "Total amount limit available against a card", example = "1000")
        @Positive(message = "totalLimit should be higher than zero")
        private int totalLimit;

        @Schema(description = "Total amount used by a Customer", example = "1000")
        @PositiveOrZero(message = "amountUsed should be higher or equal than zero")
        private int amountUsed;

        @Schema(description = "Total available amount against a card", example = "90000")
        @PositiveOrZero(message = "Total avaibleAmount should be higher or equal than zero")
        private int availableAmount;
}
