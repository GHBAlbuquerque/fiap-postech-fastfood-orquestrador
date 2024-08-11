package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PaymentGatewayImpl implements PaymentGateway {

    @Autowired
    private MessageSender messageSender;

    @Value("${aws.queue_comando_solicitar_pagamento.url}")
    private String queuePaymentCreation;

    @Value("${aws.queue_comando_realizar_pagamento.url}")
    private String queuePaymentChage;

    @Value("${aws.comando_estornar_pagamento.url}")
    private String queuePaymentReversal;

    @Value("${aws.comando_cancelar_solicitacao_pagamento.url}")
    private String queuePaymentCancellation;

    private static final Logger logger = LogManager.getLogger(PaymentGatewayImpl.class);

    @Override
    public void commandPaymentCreation(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CREATION",
                "Payment Creation",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queuePaymentCreation
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CREATION",
                    "Payment Creation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CREATION",
                    "Payment Creation",
                    ex.getMessage(),
                    message.toString());

        }
    }

    @Override
    public void commandPaymentCharge(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CREATION",
                "Payment Charge",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queuePaymentChage
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CREATION",
                    "Payment Charge");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CREATION",
                    "Payment Charge",
                    ex.getMessage(),
                    message.toString());

        }
    }

    @Override
    public void commandPaymentReversal(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CANCELLATION",
                "Payment Reversal",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queuePaymentReversal
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CANCELLATION",
                    "Payment Reversal");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CANCELLATION",
                    "Payment Reversal",
                    ex.getMessage(),
                    message.toString());

        }
    }

    @Override
    public void commandPaymentCancellation(CustomQueueMessage<String> message) {
        logger.info(String.format(
                LoggingPattern.COMMAND_INIT_LOG,
                "ORDER CANCELLATION",
                "Payment Cancellation",
                message.toString()
        ));

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getOrderId(),
                    queuePaymentCancellation
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    "ORDER CANCELLATION",
                    "Payment Cancellation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    "ORDER CANCELLATION",
                    "Payment Cancellation",
                    ex.getMessage(),
                    message.toString());

        }
    }
}
