package com.eazybytes.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardDto {
    @NotEmpty(message = "mobileNumber cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "cardNumber cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card number must be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "cardType cannot be empty or null")
    private String cardType;

    @Positive(message = "totalLimit should be higher than zero")
    private Long totalLimit;

    @PositiveOrZero(message = "amountUsed should be higher or equal than zero")
    private Long amountUsed;

    @PositiveOrZero(message = "Total avaibleAmount should be higher or equal than zero")
    private Long avaibleAmount;
}
