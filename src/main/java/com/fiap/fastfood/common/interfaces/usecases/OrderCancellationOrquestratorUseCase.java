package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;

public interface OrderCancellationOrquestratorUseCase {

    void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                     OrderGateway orderGateway,
                     PaymentGateway paymentGateway,
                     OrquestrationGateway orquestrationGateway) throws OrderCreationException;

    void reversePayment(CustomQueueMessage<CreateOrderResponse> response,
                        PaymentGateway paymentGateway,
                        OrquestrationGateway orquestrationGateway);

    void cancelPayment(CustomQueueMessage<CreateOrderResponse> response,
                       PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway);

    void cancelOrder(CustomQueueMessage<CreateOrderResponse> response,
                     OrderGateway orderGateway,
                     OrquestrationGateway orquestrationGateway);
}
