package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.builders.OrderBuilder;
import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class CreateOrderController {

    private final OrderCreationOrquestratorUseCase useCase;
    private final OrderGateway orderGateway;
    private final OrquestrationGateway orquestrationGateway;

    public CreateOrderController(OrderGateway orderGateway, OrderCreationOrquestratorUseCase orderUseCase, OrquestrationGateway orquestrationGateway) {
        this.useCase = orderUseCase;
        this.orderGateway = orderGateway;
        this.orquestrationGateway = orquestrationGateway;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreateOrderRequest> createOrder(
            @Valid @RequestBody CreateOrderRequest request) throws OrderCreationException {

        useCase.createOrder(
                OrderBuilder.fromRequestToDomain(request),
                orderGateway,
                orquestrationGateway);

        return ResponseEntity.status(HttpStatus.OK).body(request);
    }
}
