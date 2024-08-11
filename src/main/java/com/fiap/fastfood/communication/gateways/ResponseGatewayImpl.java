package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;
import com.fiap.fastfood.common.interfaces.gateways.ResponseGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseGatewayImpl implements ResponseGateway {

    private static final Logger logger = LogManager.getLogger(ResponseGatewayImpl.class);

    @SqsListener(queueNames = "${queue_resposta_criar_pedido}", maxConcurrentMessages = "1")
    public void listenToCreateOrderResponse(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.RESPONSE_INIT_LOG,
                message.getHeaders().getMicrosservice()
        ));

        try {

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());
        }
    }

    @SqsListener(queueNames = "${test_queue}", maxConcurrentMessages = "1")
    public void listenToTestQueue(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.RESPONSE_INIT_LOG,
                message.getHeaders().getMicrosservice()
        ));

        try {

            logger.info(LoggingPattern.RESPONSE_END_LOG,
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.RESPONSE_ERROR_LOG,
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());
        }
    }
}
