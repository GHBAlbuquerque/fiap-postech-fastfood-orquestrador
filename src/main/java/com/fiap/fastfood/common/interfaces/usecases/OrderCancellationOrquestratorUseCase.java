package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;

public interface OrderCancellationOrquestratorUseCase {

    void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                     OrderGateway orderGateway,
                     PaymentGateway paymentGateway,
                     OrquestrationGateway orquestrationGateway) throws OrderCancellationException;

    void reversePayment(CustomQueueMessage<CreateOrderResponse> response,
                        PaymentGateway paymentGateway,
                        OrquestrationGateway orquestrationGateway) throws OrderCancellationException;

    void cancelPayment(CustomQueueMessage<CreateOrderResponse> response,
                       PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway) throws OrderCancellationException;

    void cancelOrder(CustomQueueMessage<CreateOrderResponse> response,
                     OrderGateway orderGateway,
                     OrquestrationGateway orquestrationGateway) throws OrderCancellationException;
}
