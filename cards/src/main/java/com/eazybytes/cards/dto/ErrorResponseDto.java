package com.eazybytes.cards.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(name = "Error Response", description = "Schema to hold error response information")
public class ErrorResponseDto extends ResponseDto {
        @Schema(description = "API path invoked by client")
        private String apiPath;

        @Schema(description = "Time representing when the error happened")
        private LocalDateTime errorTime;

        @Schema(description = "Errors information.")
        private Object errors;
}
