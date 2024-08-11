package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class OrderGatewayImpl implements OrderGateway {

    @Autowired
    private MessageSender messageSender;

    @Value("${aws.queue_comando_criar_pedido.url}")
    private String queueCreateOrder;

    @Value("${aws.queue_comando_preparar_pedido.url}")
    private String queuePrepareOrder;

    @Value("${aws.queue_comando_encerrar_pedido.url}")
    private String queueCompleteOrder;

    @Value("${aws.comando_cancelar_pedido.url}")
    private String queueCancelOrder;

    private static final Logger logger = LogManager.getLogger(OrderGatewayImpl.class);

    @Override
    public void commandOrderCreation(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CREATION",
                "Order Creation",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queueCreateOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CREATION",
                    "Order Creation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CREATION",
                    "Order Creation",
                    ex.getMessage(),
                    message.toString());

        }
    }

    @Override
    public void commandOrderPreparation(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CREATION",
                "Order Preparation",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queueCreateOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CREATION",
                    "Order Preparation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CREATION",
                    "Order Preparation",
                    ex.getMessage(),
                    message.toString());

        }
    }

    @Override
    public void commandOrderCompletion(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CREATION",
                "Order Completion",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queueCreateOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CREATION",
                    "Order Completion");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CREATION",
                    "Order Completion",
                    ex.getMessage(),
                    message.toString());

        }
    }

    @Override
    public void commandOrderCancellation(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CANCELLATION",
                "Order Cancellation",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queueCreateOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CANCELLATION",
                    "Order Cancellation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CANCELLATION",
                    "Order Cancellation",
                    ex.getMessage(),
                    message.toString());

        }
    }
}
