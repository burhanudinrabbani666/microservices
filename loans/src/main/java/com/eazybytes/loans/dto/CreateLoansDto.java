package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Schema for hold create Loans details", name = "Create Loans DTO")
public class CreateLoansDto {
        @NotEmpty(message = "Mobile Number can not be a null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
        @Schema(description = "Mobile Number of Customer", example = "0812345678")
        private String mobileNumber;
}
