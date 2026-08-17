package com.eazybytes.loans.dto;

import com.eazybytes.loans.entity.Loans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(name = "Loans Success Response", description = "Schema to hold Success response information")
public class LoansSuccessResponseDto extends ResponseDto {
        private Loans data;
}