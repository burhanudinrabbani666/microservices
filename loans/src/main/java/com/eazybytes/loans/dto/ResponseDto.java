package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Base Response Schema", description = "Schema to hold successful response information")
public class ResponseDto {
        @Schema(description = "Status code in the response")
        private String statusCode;

        @Schema(description = "Status message in the response")
        private String statusMessage;
}