package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.core.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderCreationOrquestratorUseCaseImpl implements OrderCreationOrquestratorUseCase {

    private static final Logger logger = LogManager.getLogger(OrderCreationOrquestratorUseCaseImpl.class);

    @Override
    public void createOrder(Order order, OrderGateway orderGateway) {
        logger.info(
                LoggingPattern.ORQUESTRATION_INIT_LOG,
                "createOrder",
                order.toString()
        );

        try {

            /*final var headers = new CustomMessageHeaders();
            final var message = new CustomQueueMessage<CreateOrderCommand>();

            orderGateway.commandOrderCreation();*/

        } catch (Exception ex) {
            logger.info(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    "createOrder",
                    order.toString()
            );
        }
    }

    @Override
    public void prepareOrder(OrderGateway orderGateway) {

    }

    @Override
    public void completeOrder(OrderGateway orderGateway) {

    }

    @Override
    public void notifyCustomer(CustomerGateway customerGateway) {

    }

    @Override
    public void createPayment(PaymentGateway paymentGateway) {

    }

    @Override
    public void chargePayment(PaymentGateway paymentGateway) {

    }
}
