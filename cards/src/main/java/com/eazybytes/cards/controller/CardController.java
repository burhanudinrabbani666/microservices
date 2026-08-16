package com.eazybytes.cards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.dto.CardSuccessResponseDto;
import com.eazybytes.cards.dto.CreateCardDto;
import com.eazybytes.cards.dto.ErrorResponseDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.service.ICardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cards", description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details")
@RestController()
@RequestMapping(path = "/api/cards")
@RequiredArgsConstructor
@Validated
public class CardController {

        private final ICardService cardService;

        /**
         * 
         * @param mobiileNumber
         * @return
         */
        @Operation(summary = "Get Card ", description = "REST API to fetch card details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "OK"),
                        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @GetMapping(path = "/{mobileNumber}")
        public ResponseEntity<CardSuccessResponseDto> getCardByMobileNumber(
                        @PathVariable @Valid @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits") String mobileNumber) {
                Cards card = this.cardService.getCardByMobileNumber(mobileNumber);
                CardSuccessResponseDto response = new CardSuccessResponseDto();

                response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
                response.setStatusMessage("Success get Card Details");
                response.setCard(card);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * 
         * @param createCardDto
         * @return
         */
        @Operation(summary = "Create Card", description = "REST API to create new Card inside EazyBank")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "CREATED"),
                        @ApiResponse(responseCode = "404", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "409", description = "CONFLICT", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PostMapping
        public ResponseEntity<CardSuccessResponseDto> createCard(@RequestBody @Valid CreateCardDto createCardDto) {
                Cards card = this.cardService.createCard(createCardDto);
                CardSuccessResponseDto response = new CardSuccessResponseDto();

                response.setStatusCode(String.valueOf(HttpStatus.CREATED.value()));
                response.setStatusMessage("Success create Card Details");
                response.setCard(card);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        /**
         * 
         * @param id
         * @param cardDto
         * @return
         */
        @Operation(summary = "Update Card", description = "REST API to update card details based on a card number")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "NO CONTENT"),
                        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "409", description = "CONFLICT", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @PutMapping(path = "/{id}")
        public ResponseEntity<String> updatedCard(@PathVariable @Valid Long id, @RequestBody @Valid CardDto cardDto) {
                this.cardService.updateCard(id, cardDto);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card successfully updated");
        }

        /**
         * 
         * @param mobileNumber
         * @return
         */
        @Operation(summary = "Delete Card", description = "REST API to delete Card details based on a mobile number")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "NO CONTENT"),
                        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
        })
        @DeleteMapping(path = "/{mobileNumber}")
        public ResponseEntity<String> deleteCardByMobileNumber(@PathVariable String mobileNumber) {
                this.cardService.deleteCardByMobileNumber(mobileNumber);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Card successfully Deleted");
        }
}
