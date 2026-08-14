package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer information")
public class CustomerDto {

    @Schema(example = "burhanudin rabbani", description = "Name of customer")
    @NotEmpty(message = "Name cannot be nul or empty")
    @Size(min = 5, max = 30, message = "The length of the customer should be between 5 and 30")
    private String name;

    @Schema(example = "rabbani@example.com", description = "Email of customer")
    @NotEmpty(message = "Email address cannot be empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(example = "0812345678", description = "Mobile number of cutomer")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Accounts details of customer")
    private AccountsDto accountsDto;
}
