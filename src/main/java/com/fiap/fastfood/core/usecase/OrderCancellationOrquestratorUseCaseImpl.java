package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.command.PaymentCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
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
import static com.fiap.fastfood.common.logging.LoggingPattern.ORQUESTRATION_STEP_INFORMER;

public class OrderCancellationOrquestratorUseCaseImpl implements OrderCancellationOrquestratorUseCase {

    private static final Logger logger = LogManager.getLogger(OrderCancellationOrquestratorUseCaseImpl.class);

    @Override
    public void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                            OrderGateway orderGateway,
                            PaymentGateway paymentGateway,
                            OrquestrationGateway orquestrationGateway) throws OrderCancellationException {

        final var failedOrExecutedStep = message.getBody().getExecutedStep();

        logger.info(ORQUESTRATION_STEP_INFORMER,
                message.getHeaders().getSagaId(),
                failedOrExecutedStep);

        switch (failedOrExecutedStep) {
            case PREPARE_ORDER:
                reversePayment(message, paymentGateway, orquestrationGateway);
                break;
            case CHARGE_PAYMENT, REVERSE_PAYMENT:
                cancelPayment(message, paymentGateway, orquestrationGateway);
                break;
            case CREATE_PAYMENT, CANCEL_PAYMENT:
                cancelOrder(message, orderGateway, orquestrationGateway);
                break;
            default:
                throw new OrderCancellationException(SAGA_12_ORQUESTRATION_STEP_NR,
                        "Orquestration Step not recognized or does not have Compensating Transactions."
                );
        }
    }

    @Override
    public void reversePayment(CustomQueueMessage<CreateOrderResponse> response,
                               PaymentGateway paymentGateway,
                               OrquestrationGateway orquestrationGateway) throws OrderCancellationException {
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

            throw new OrderCancellationException(ExceptionCodes.SAGA_07_PAYMENT_REVERSAL, ex.getMessage());
        }
    }

    @Override
    public void cancelPayment(CustomQueueMessage<CreateOrderResponse> response,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) throws OrderCancellationException {
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

            throw new OrderCancellationException(ExceptionCodes.SAGA_08_PAYMENT_CANCELLATION, ex.getMessage());
        }
    }

    @Override
    public void cancelOrder(CustomQueueMessage<CreateOrderResponse> response,
                            OrderGateway orderGateway,
                            OrquestrationGateway orquestrationGateway) throws OrderCancellationException {
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

            throw new OrderCancellationException(ExceptionCodes.SAGA_09_ORDER_CANCELLATION, ex.getMessage());
        }
    }
}
