package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Account", description = "Schema to hold Account information")
public class AccountsDto {

    @Schema(description = "Account number of EazyBank Account", example = "123456789")
    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account Type of EazyBank Account", example = "Savings")
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;

    @Schema(description = "Branch Address of EazyBank Account", example = "123 NewYork")
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;
}
