package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
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

import static com.fiap.fastfood.common.logging.Constants.MESSAGE_TYPE_COMMAND;
import static com.fiap.fastfood.common.logging.Constants.MS_ORDER;

public class OrderCreationOrquestratorUseCaseImpl implements OrderCreationOrquestratorUseCase {

    private static final Logger logger = LogManager.getLogger(OrderCreationOrquestratorUseCaseImpl.class);

    @Override
    public void createOrder(Order order,
                            OrderGateway orderGateway,
                            OrquestrationGateway orquestrationGateway) {
        logger.info(
                LoggingPattern.ORQUESTRATION_INIT_LOG,
                "createOrder",
                order.toString()
        );

        try {

            final var id = orquestrationGateway.createStepRecord(
                    OrquestrationStepEnum.ORDER_REQUESTED.name()
            );

            final var headers = new CustomMessageHeaders(id, null, MESSAGE_TYPE_COMMAND, MS_ORDER);
            final var message = new CustomQueueMessage<CreateOrderCommand>(headers,
                    new CreateOrderCommand(order.getCustomerId(), order.getItems())
            );

            orderGateway.commandOrderCreation(message);

            logger.info(
                    LoggingPattern.ORQUESTRATION_END_LOG,
                    id,
                    "createOrder"
            );

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.ORQUESTRATION_ERROR_LOG,
                    "Id not created",
                    "createOrder"
            );
        }
    }

    @Override
    public void createPayment(PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        logger.info(
                LoggingPattern.ORQUESTRATION_STEP_LOG,
                "createPayment",
                order.toString()
        );

        try {


        } catch (Exception ex) {


        }
    }

    @Override
    public void chargePayment(PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void prepareOrder(OrderGateway orderGateway, OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void completeOrder(OrderGateway orderGateway, OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void notifyCustomer(CustomerGateway customerGateway, OrquestrationGateway orquestrationGateway) {

    }

}
