package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.NotifyCustomerCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class CustomerGatewayImpl implements CustomerGateway {

    private final MessageSender messageSender;

    public CustomerGatewayImpl(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Value("${aws.queue_comando_notificar_cliente.url}")
    private String queueCustomerNotification;

    private static final Logger logger = LogManager.getLogger(CustomerGatewayImpl.class);

    @Override
    public void commandCustomerNotification(CustomQueueMessage<NotifyCustomerCommand> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Customer Notification",
                message
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queueCustomerNotification
            );

            logger.info(
                    LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Customer Notification"
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Customer Notification",
                    ex.getMessage(),
                    message
            );

            throw new OrderCreationException(ExceptionCodes.SAGA_01_CUSTOMER_NOTIFICATION, ex.getMessage());
        }

    }
}
