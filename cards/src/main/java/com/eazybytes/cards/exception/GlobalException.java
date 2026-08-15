package com.eazybytes.cards.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

        @ExceptionHandler(CardAlreadyExistException.class)
        public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(CardAlreadyExistException ex,
                        WebRequest webRequest) {

                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.CONFLICT.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.CONFLICT.getReasonPhrase());
                errorResponseDTO.setErrors(ex.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex,
                        WebRequest webRequest) {

                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                errorResponseDTO.setErrors(ex.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        /**
         * @exception Exception
         * @param ex
         * @param webRequest
         * @return
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest webRequest) {
                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                errorResponseDTO.setErrors(ex.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
        }

        @Override
        protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                Map<String, String> validationError = new HashMap<>();
                List<ObjectError> listOfError = ex.getBindingResult().getAllErrors();

                listOfError.forEach(error -> {
                        String fieldError = ((FieldError) error).getField();
                        String fieldMessage = error.getDefaultMessage();
                        validationError.put(fieldError, fieldMessage);
                });

                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(request.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                errorResponseDTO.setErrors(validationError);
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);

        }

}
