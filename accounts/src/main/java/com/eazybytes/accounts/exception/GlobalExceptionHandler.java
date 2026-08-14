package com.eazybytes.accounts.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
