package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CreateOrderController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreateOrderRequest> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        /*final var result = useCase.createOrder(
                OrderBuilder.fromRequestToDomain(request),
                gateway,
                productGateway,
                customerGateway);*/

        return ResponseEntity.status(HttpStatus.OK).body(request);
    }
}
