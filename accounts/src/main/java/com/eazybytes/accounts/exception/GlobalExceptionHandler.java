package com.eazybytes.accounts.exception;

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

import com.eazybytes.accounts.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @exception CustomerAlreadyExistsException
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex,
            WebRequest webRequest) {

        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        errorResponseDTO.setErrorCode(HttpStatus.CONFLICT);
        errorResponseDTO.setErrorMessage(ex.getMessage());
        errorResponseDTO.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    /**
     * @exception ResourceNotFoundException
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest webRequest) {

        ErrorResponseDto errorResponseDTO = new ErrorResponseDto();
        errorResponseDTO.setApiPath(webRequest.getDescription(false));
        errorResponseDTO.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponseDTO.setErrorMessage(ex.getMessage());
        errorResponseDTO.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
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
        errorResponseDTO.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponseDTO.setErrorMessage(ex.getMessage());
        errorResponseDTO.setErrorTime(LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> validationError = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(error -> {
            String fieldError = ((FieldError) error).getField();
            String validationMessage = error.getDefaultMessage();
            validationError.put(fieldError, validationMessage);
        });

        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

}
