package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Order;

public interface OrderCreationOrquestratorUseCase {

    void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                     OrderGateway orderGateway,
                     PaymentGateway paymentGateway,
                     OrquestrationGateway orquestrationGateway) throws OrderCreationException;

    void createOrder(Order order,
                     OrderGateway orderGateway,
                     OrquestrationGateway orquestrationGateway);

    void createPayment(CustomQueueMessage<CreateOrderResponse> message,
                       PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway);

    void chargePayment(CustomQueueMessage<CreateOrderResponse> message,
                       PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway);

    void prepareOrder(CustomQueueMessage<CreateOrderResponse> message,
                      OrderGateway orderGateway,
                      OrquestrationGateway orquestrationGateway);

    void completeOrder(CustomQueueMessage<CreateOrderResponse> message,
                       OrderGateway orderGateway,
                       OrquestrationGateway orquestrationGateway);

    void notifyCustomer(CustomQueueMessage<CreateOrderResponse> message,
                        CustomerGateway customerGateway,
                        OrquestrationGateway orquestrationGateway);

}
