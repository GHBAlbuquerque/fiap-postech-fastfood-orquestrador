package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.CreateOrderRequest;
import com.fiap.fastfood.common.dto.request.OrderItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreateOrderControllerTest {

    @Value("${server.port}")
    private int port;

    @Test
    void givenCreateOrderRequestThenRespondWithStatusCreated() {
        final var orderRequest = createOrderRequest();

        given()
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post("/orders")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    private CreateOrderRequest createOrderRequest() {
        final var item = new OrderItemRequest("0d60f617-f59e-4f88-8f04-4ad35c8248c9", "SANDWICH", 1, BigDecimal.ONE);

        return new CreateOrderRequest(1L, List.of(item));
    }
}
