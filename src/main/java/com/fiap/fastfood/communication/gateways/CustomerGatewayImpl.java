package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class CustomerGatewayImpl implements CustomerGateway {

    @Autowired
    private MessageSender messageSender;

    @Value("${aws.queue_comando_notificar_cliente.url}")
    private String queueCustomerNotification;

    private static final Logger logger = LogManager.getLogger(CustomerGatewayImpl.class);

    @Override
    public void commandCustomerNotification(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CREATION",
                "Customer Notification",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queueCustomerNotification
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CREATION",
                    "Customer Notification");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CREATION",
                    "Customer Notification",
                    ex.getMessage(),
                    message.toString());
        }

    }
}
