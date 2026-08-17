package com.eazybytes.loans.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.eazybytes.loans.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHanlder extends ResponseEntityExceptionHandler {

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                Map<String, String> validationErrors = new HashMap<>();
                List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

                validationErrorList.forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String validationMsg = error.getDefaultMessage();
                        validationErrors.put(fieldName, validationMsg);
                });

                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(request.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                errorResponseDTO.setErrors(validationErrorList);
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
        }

        /**
         * 
         * @param exception
         * @param webRequest
         * @return
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest) {
                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                errorResponseDTO.setErrors(exception.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
        }

        /**
         * 
         * @param exception
         * @param webRequest
         * @return
         */
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                        WebRequest webRequest) {
                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                errorResponseDTO.setErrors(exception.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        /**
         * 
         * @param exception
         * @param webRequest
         * @return
         */
        @ExceptionHandler(LoanAlreadyExistsException.class)
        public ResponseEntity<ErrorResponseDto> handleLoanAlreadyExistsException(LoanAlreadyExistsException exception,
                        WebRequest webRequest) {
                ErrorResponseDto errorResponseDTO = new ErrorResponseDto();

                errorResponseDTO.setApiPath(webRequest.getDescription(false));
                errorResponseDTO.setStatusCode(String.valueOf(HttpStatus.CONFLICT.value()));
                errorResponseDTO.setStatusMessage(HttpStatus.CONFLICT.getReasonPhrase());
                errorResponseDTO.setErrors(exception.getMessage());
                errorResponseDTO.setErrorTime(LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
        }

}
