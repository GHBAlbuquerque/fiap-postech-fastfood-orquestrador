package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.PaymentCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.logging.LoggingPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class PaymentGatewayImpl implements PaymentGateway {

    private final MessageSender messageSender;

    public PaymentGatewayImpl(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

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
    public void commandPaymentCreation(CustomQueueMessage<PaymentCommand> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Payment Creation",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queuePaymentCreation
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Creation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Creation",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_05_PAYMENT_CREATION, ex.getMessage());

        }
    }

    @Override
    public void commandPaymentCharge(CustomQueueMessage<PaymentCommand> message) throws OrderCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Payment Charge",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queuePaymentChage
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Charge");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Charge",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCreationException(ExceptionCodes.SAGA_06_PAYMENT_CHARGE, ex.getMessage());

        }
    }

    @Override
    public void commandPaymentReversal(CustomQueueMessage<PaymentCommand> message) throws OrderCancellationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Payment Reversal",
                message.toString()
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queuePaymentReversal
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Reversal");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Reversal",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCancellationException(ExceptionCodes.SAGA_07_PAYMENT_REVERSAL, ex.getMessage());

        }

    }

    @Override
    public void commandPaymentCancellation(CustomQueueMessage<PaymentCommand> message) throws OrderCancellationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                "Payment Cancellation",
                message
        );

        try {
            messageSender.sendMessage(
                    message,
                    message.getHeaders().getSagaId(),
                    queuePaymentCancellation
            );

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Cancellation");

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    "Payment Cancellation",
                    ex.getMessage(),
                    message.toString());

            throw new OrderCancellationException(ExceptionCodes.SAGA_08_PAYMENT_CANCELLATION, ex.getMessage());

        }
    }
}
