package com.eazybytes.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Schema for creating EazyBank Card", name = "Create Card Schema")
public class CreateCardDto {

        @NotEmpty
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
        @Schema(description = "Mobile number of EazyBank Card", example = "0812345678")
        private String mobileNumber;

}
