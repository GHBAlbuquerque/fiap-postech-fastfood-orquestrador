package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class OrderGatewayImpl implements OrderGateway {

    private final MessageSender messageSender;

    public OrderGatewayImpl(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

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
    public void commandOrderCreation(CustomQueueMessage<CreateOrderCommand> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Order Creation",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queueCreateOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Creation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Creation",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_02_ORDER_CREATION, ex.getMessage());

        }
    }

    @Override
    public void commandOrderPreparation(CustomQueueMessage<OrderCommand> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Order Preparation",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queuePrepareOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Preparation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Preparation",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_03_ORDER_PREPARATION, ex.getMessage());

        }

    }

    @Override
    public void commandOrderCompletion(CustomQueueMessage<OrderCommand> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Order Completion",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queueCompleteOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Completion");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Completion",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_04_ORDER_COMPLETION, ex.getMessage());

        }
    }

    @Override
    public void commandOrderCancellation(CustomQueueMessage<OrderCommand> message) throws OrderCancellationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Order Cancellation",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queueCancelOrder
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Cancellation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Order Cancellation",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCancellationException(ExceptionCodes.SAGA_09_ORDER_CANCELLATION, ex.getMessage());

        }
    }
}
