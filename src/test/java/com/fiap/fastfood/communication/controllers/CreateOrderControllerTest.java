package com.fiap.fastfood.communication.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreateOrderControllerTest {

    @Value("${server.port}")
    private int port;
}
