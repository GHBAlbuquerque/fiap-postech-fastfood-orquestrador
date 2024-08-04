package com.fiap.fastfood.communication.gateways;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderGateway {

    private static final Logger logger = LogManager.getLogger(OrderGateway.class);

    @SqsListener("${aws.queue_comando_criar_pedido.url}")
    public void listenerCriarPedido(String message) {
        System.out.println(message);
    }

    @SqsListener("${aws.queue_comando_criar_pedido.url}")
    public void listenTwoQueues(String message) {
        System.out.println(message);
    }
}
