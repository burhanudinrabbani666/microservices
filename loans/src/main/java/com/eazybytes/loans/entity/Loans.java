package com.eazybytes.loans.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Loans", description = "Laons Details Information")
public class Loans extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "Loans id", example = "1")
        private Long loanId;

        @Schema(description = "Mobile number customer", example = "0812345678")
        private String mobileNumber;

        @Schema(description = "Loans number customer", example = "112233445566")
        private String loanNumber;

        @Schema(description = "Loans type customer", example = "Home Loan")
        private String loanType;

        @Schema(description = "Total loan amount", example = "100000")
        private int totalLoan;

        @Schema(description = "Total loan amount paid", example = "1000")
        private int amountPaid;

        @Schema(description = "Total outstanding amount against a loan", example = "99000")
        private int outstandingAmount;
}