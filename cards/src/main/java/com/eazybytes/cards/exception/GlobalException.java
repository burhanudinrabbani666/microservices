package com.eazybytes.cards.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

        @ExceptionHandler(CardAlreadyExistException.class)
        public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(
                        CardAlreadyExistException ex, WebRequest webRequest) {

                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.CONFLICT.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.CONFLICT.getReasonPhrase());
                errorResponseDTO.setErrors(ex.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceNotFound(
                        ResourceNotFoundException ex, WebRequest webRequest) {

                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                errorResponseDTO.setErrors(ex.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

}
