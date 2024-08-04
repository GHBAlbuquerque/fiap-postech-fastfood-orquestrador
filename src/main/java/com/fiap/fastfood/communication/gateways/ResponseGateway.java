package com.fiap.fastfood.communication.gateways;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseGateway {

    private static final Logger logger = LogManager.getLogger(ResponseGateway.class);

    @SqsListener(queueNames = "${queue_resposta_criar_pedido}", maxConcurrentMessages = "1")
    public void listenerCreateOrderResponse(String message) {
        System.out.println(message);
    }
}
