package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.command.PaymentCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.SAGA_12_ORQUESTRATION_STEP_NR;
import static com.fiap.fastfood.common.logging.Constants.*;

public class OrderCancellationOrquestratorUseCaseImpl implements OrderCancellationOrquestratorUseCase {

    private static final Logger logger = LogManager.getLogger(OrderCancellationOrquestratorUseCaseImpl.class);

    @Override
    public void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                            OrderGateway orderGateway,
                            PaymentGateway paymentGateway,
                            OrquestrationGateway orquestrationGateway) throws OrderCreationException {

        final var executedStep = message.getBody().getExecutedStep();

        switch (executedStep) {
            case REVERSE_PAYMENT:
                cancelPayment(message, paymentGateway, orquestrationGateway);
            case CANCEL_PAYMENT:
                cancelOrder(message, orderGateway, orquestrationGateway);
            default:
                throw new OrderCreationException(SAGA_12_ORQUESTRATION_STEP_NR, "Orquestration Step not recognized.");
        }
    }

    @Override
    public void reversePayment(CustomQueueMessage<CreateOrderResponse> response,
                               PaymentGateway paymentGateway,
                               OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.REVERSE_PAYMENT.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.REVERSE_PAYMENT.name(),
                    orderId
            );

            final var headers = new CustomMessageHeaders(id, orderId, MESSAGE_TYPE_COMMAND, MS_PAYMENT);
            final var message = new CustomQueueMessage<>(
                    headers,
                    new PaymentCommand(orderId, customerId, paymentId)
            );

            paymentGateway.commandPaymentCreation(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    OrquestrationStepEnum.REVERSE_PAYMENT.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.REVERSE_PAYMENT.name(),
                    ex.getMessage(),
                    response
            );
        }
    }

    @Override
    public void cancelPayment(CustomQueueMessage<CreateOrderResponse> response,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.CANCEL_PAYMENT.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.CANCEL_PAYMENT.name(),
                    orderId
            );

            final var headers = new CustomMessageHeaders(id, orderId, MESSAGE_TYPE_COMMAND, MS_PAYMENT);
            final var message = new CustomQueueMessage<>(
                    headers,
                    new PaymentCommand(orderId, customerId, paymentId)
            );

            paymentGateway.commandPaymentCreation(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    OrquestrationStepEnum.CANCEL_PAYMENT.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.CANCEL_PAYMENT.name(),
                    ex.getMessage(),
                    response
            );
        }
    }

    @Override
    public void cancelOrder(CustomQueueMessage<CreateOrderResponse> response,
                            OrderGateway orderGateway,
                            OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.CANCEL_ORDER.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.CANCEL_ORDER.name(),
                    orderId
            );

            final var headers = new CustomMessageHeaders(id, orderId, MESSAGE_TYPE_COMMAND, MS_ORDER);
            final var message = new CustomQueueMessage<>(
                    headers,
                    new OrderCommand(orderId, customerId, paymentId)
            );

            orderGateway.commandOrderPreparation(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    OrquestrationStepEnum.CANCEL_ORDER.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.PREPARE_ORDER.name(),
                    ex.getMessage(),
                    response
            );
        }
    }


}
