package com.eazybytes.cards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Cards", description = "Cards details information")
public class Cards extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long cardId;

        @Schema(description = "Mobile number of EazyBank Card", example = "0812345678")
        private String mobileNumber;

        @Schema(description = "Card number of EazyBank Card", example = "112233445566")
        private String cardNumber;

        @Schema(description = "Card Type of EazyBank Card", example = "savings")
        private String cardType;

        @Schema(description = "Total amount limit available against a card", example = "1000")
        private int totalLimit;

        @Schema(description = "Total amount used by a Customer", example = "1000")
        private int amountUsed;

        @Schema(description = "Total available amount against a card", example = "90000")
        private int availableAmount;
}
