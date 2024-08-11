package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.ResponseGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseGatewayImpl implements ResponseGateway {

    private static final Logger logger = LogManager.getLogger(ResponseGatewayImpl.class);

    @SqsListener(queueNames = "${queue_resposta_criar_pedido}", maxConcurrentMessages = "1")
    public void listenToCreateOrderResponse(CustomQueueMessage<String> message) throws OrderCreationException {
        logger.info(
                message.getHeaders().getOrderId(),
                LoggingPattern.RESPONSE_INIT_LOG,
                message.getHeaders().getMicrosservice()
        );

        try {

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getOrderId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    message.getHeaders().getOrderId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_10_ORDER_RESPONSE_PROCESSING, ex.getMessage());
        }
    }

    @SqsListener(queueNames = "${test_queue}", maxConcurrentMessages = "1")
    public void listenToTestQueue(CustomQueueMessage<String> message) {
        logger.info(
                LoggingPattern.RESPONSE_INIT_LOG,
                message.getHeaders().getOrderId(),
                message.getHeaders().getMicrosservice()
        );

        try {

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getOrderId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    message.getHeaders().getOrderId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());
        }
    }
}
