package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;
import com.fiap.fastfood.common.interfaces.gateways.ResponseGateway;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseGatewayImpl implements ResponseGateway {

    private static final Logger logger = LogManager.getLogger(ResponseGatewayImpl.class);

    @SqsListener(queueNames = "${queue_resposta_criar_pedido}", maxConcurrentMessages = "1")
    public void listenToCreateOrderResponse(CustomQueueMessage<String> message) {
        System.out.println(message);
    }

    @SqsListener(queueNames = "${queue_resposta_criar_pedido}", maxConcurrentMessages = "1")
    public void listenToTestQueue(CustomQueueMessage<String> message) {
        System.out.println(message);
    }
}
