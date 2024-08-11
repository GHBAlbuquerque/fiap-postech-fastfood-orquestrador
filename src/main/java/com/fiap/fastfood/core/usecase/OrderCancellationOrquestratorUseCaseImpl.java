package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.SAGA_12_ORQUESTRATION_STEP_NR;

public class OrderCancellationOrquestratorUseCaseImpl implements OrderCancellationOrquestratorUseCase {


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

    }

    @Override
    public void cancelPayment(CustomQueueMessage<CreateOrderResponse> response,
                              PaymentGateway paymentGateway,
                              OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void cancelOrder(CustomQueueMessage<CreateOrderResponse> response,
                            OrderGateway orderGateway,
                            OrquestrationGateway orquestrationGateway) {

    }


}
