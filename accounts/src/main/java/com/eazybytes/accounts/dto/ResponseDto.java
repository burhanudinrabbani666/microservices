package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Response", description = "Schema to hold Succefull response information")
public class ResponseDto {

    @Schema(description = "Status code of Success response")
    private String statusCode;

    @Schema(description = "Status message of Success response")
    private String statusMessage;
}
