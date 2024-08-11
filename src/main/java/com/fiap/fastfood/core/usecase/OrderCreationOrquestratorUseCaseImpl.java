package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.NotifyCustomerCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.command.PaymentCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.SAGA_12_ORQUESTRATION_STEP_NR;
import static com.fiap.fastfood.common.logging.Constants.*;

public class OrderCreationOrquestratorUseCaseImpl implements OrderCreationOrquestratorUseCase {

    //TODO: criar contagem de recebimentos
    //TODO: criar chamada de métodos compensatórios
    //TODO: criar chamada para notificacao cliente

    private static final Logger logger = LogManager.getLogger(OrderCreationOrquestratorUseCaseImpl.class);

    @Override
    public void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                            OrderGateway orderGateway,
                            PaymentGateway paymentGateway,
                            OrquestrationGateway orquestrationGateway) throws OrderCreationException {

        final var executedStep = message.getBody().getExecutedStep();

        switch (executedStep) {
            case CREATE_ORDER:
                createPayment(message, paymentGateway, orquestrationGateway);
            case CREATE_PAYMENT:
                chargePayment(message, paymentGateway, orquestrationGateway);
            case CHARGE_PAYMENT:
                prepareOrder(message, orderGateway, orquestrationGateway);
            case PREPARE_ORDER:
                completeOrder(message, orderGateway, orquestrationGateway);
            default:
                throw new OrderCreationException(SAGA_12_ORQUESTRATION_STEP_NR, "Orquestration Step not recognized.");
        }
    }

    @Override
    public void createOrder(Order order,
                            OrderGateway orderGateway,
                            OrquestrationGateway orquestrationGateway) {
        logger.info(
                LoggingPattern.ORQUESTRATION_INIT_LOG,
                OrquestrationStepEnum.CREATE_ORDER.name(),
                order.toString()
        );

        try {

            final var id = orquestrationGateway.createStepRecord(
                    OrquestrationStepEnum.CREATE_ORDER.name()
            );

            final var headers = new CustomMessageHeaders(id, null, MESSAGE_TYPE_COMMAND, MS_ORDER);
            final var message = new CustomQueueMessage<CreateOrderCommand>(
                    headers,
                    new CreateOrderCommand(order.getCustomerId(), order.getItems())
            );

            orderGateway.commandOrderCreation(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    OrquestrationStepEnum.CREATE_ORDER.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    "Id not created",
                    OrquestrationStepEnum.CREATE_ORDER.name(),
                    ex.getMessage(),
                    order
            );
        }
    }

    @Override
    public void createPayment(CustomQueueMessage<CreateOrderResponse> response,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) {

        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.CREATE_PAYMENT.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.CREATE_PAYMENT.name(),
                    orderId
            );

            final var headers = new CustomMessageHeaders(id, orderId, MESSAGE_TYPE_COMMAND, MS_PAYMENT);
            final var message = new CustomQueueMessage<>(
                    headers,
                    new PaymentCommand(orderId, customerId, null)
            );

            paymentGateway.commandPaymentCreation(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    OrquestrationStepEnum.CREATE_PAYMENT.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.CREATE_PAYMENT.name(),
                    ex.getMessage(),
                    response
            );
        }
    }

    @Override
    public void chargePayment(CustomQueueMessage<CreateOrderResponse> response,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.CHARGE_PAYMENT.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.CHARGE_PAYMENT.name(),
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
                    OrquestrationStepEnum.CHARGE_PAYMENT.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.CHARGE_PAYMENT.name(),
                    ex.getMessage(),
                    response
            );
        }
    }

    @Override
    public void prepareOrder(CustomQueueMessage<CreateOrderResponse> response,
                             OrderGateway orderGateway,
                             OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.PREPARE_ORDER.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.PREPARE_ORDER.name(),
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
                    OrquestrationStepEnum.PREPARE_ORDER.name()
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

    @Override
    public void completeOrder(CustomQueueMessage<CreateOrderResponse> response,
                              OrderGateway orderGateway,
                              OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.COMPLETE_ORDER.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.COMPLETE_ORDER.name(),
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
                    OrquestrationStepEnum.COMPLETE_ORDER.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.COMPLETE_ORDER.name(),
                    ex.getMessage(),
                    response
            );
        }
    }

    @Override
    public void notifyCustomer(CustomQueueMessage<CreateOrderResponse> response,
                               CustomerGateway customerGateway,
                               OrquestrationGateway orquestrationGateway) {
        final var id = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();
        final var customerId = response.getBody().getCustomerId();
        final var paymentId = response.getBody().getPaymentId();

        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                id,
                OrquestrationStepEnum.NOTIFY_CUSTOMER.name()
        );

        try {

            orquestrationGateway.saveStepRecord(
                    id,
                    OrquestrationStepEnum.NOTIFY_CUSTOMER.name(),
                    orderId
            );

            final var headers = new CustomMessageHeaders(id, orderId, MESSAGE_TYPE_COMMAND, MS_CUSTOMER);
            final var message = new CustomQueueMessage<>(
                    headers,
                    new NotifyCustomerCommand(orderId, customerId, paymentId)
            );

            customerGateway.commandCustomerNotification(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    OrquestrationStepEnum.NOTIFY_CUSTOMER.name()
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    id,
                    OrquestrationStepEnum.NOTIFY_CUSTOMER.name(),
                    ex.getMessage(),
                    response
            );
        }
    }


}
