package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
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
import static com.fiap.fastfood.common.logging.Constants.MESSAGE_TYPE_COMMAND;
import static com.fiap.fastfood.common.logging.Constants.MS_ORDER;

public class OrderCreationOrquestratorUseCaseImpl implements OrderCreationOrquestratorUseCase {

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
            final var message = new CustomQueueMessage<CreateOrderCommand>(headers,
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
    public void createPayment(CustomQueueMessage<CreateOrderResponse> message,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void chargePayment(CustomQueueMessage<CreateOrderResponse> message,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void prepareOrder(CustomQueueMessage<CreateOrderResponse> message,
                             OrderGateway orderGateway,
                             OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void completeOrder(CustomQueueMessage<CreateOrderResponse> message,
                              OrderGateway orderGateway,
                              OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void notifyCustomer(CustomQueueMessage<CreateOrderResponse> message,
                               CustomerGateway customerGateway,
                               OrquestrationGateway orquestrationGateway) {

    }


}
