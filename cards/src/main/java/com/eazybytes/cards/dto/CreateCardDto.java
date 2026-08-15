package com.eazybytes.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateCardDto {
        @NotEmpty
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
        private String mobileNumber;
}
