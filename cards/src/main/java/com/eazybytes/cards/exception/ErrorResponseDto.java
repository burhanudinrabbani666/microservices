package com.eazybytes.cards.exception;

import java.time.LocalDateTime;

import com.eazybytes.cards.dto.ResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorResponseDto extends ResponseDto {
        private String apiPath;
        private LocalDateTime errorTime;
        private Object errors;
}
