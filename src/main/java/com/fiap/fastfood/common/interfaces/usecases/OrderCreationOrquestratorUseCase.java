package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;

public interface OrderCreationOrquestratorUseCase {

    void orquestrate(CustomQueueMessage<CreateOrderResponse> message,
                     OrderGateway orderGateway,
                     PaymentGateway paymentGateway,
                     CustomerGateway customerGateway,
                     OrquestrationGateway orquestrationGateway) throws OrderCreationException, OrderCancellationException;

    void createOrder(Order order,
                     OrderGateway orderGateway,
                     OrquestrationGateway orquestrationGateway) throws OrderCreationException;

    void createPayment(CustomQueueMessage<CreateOrderResponse> response,
                       PaymentGateway paymentGateway,
                       OrderGateway orderGateway,
                       OrquestrationGateway orquestrationGateway) throws OrderCreationException, OrderCancellationException;

    void chargePayment(CustomQueueMessage<CreateOrderResponse> response,
                       PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway) throws OrderCreationException, OrderCancellationException;

    void prepareOrder(CustomQueueMessage<CreateOrderResponse> response,
                      OrderGateway orderGateway,
                      PaymentGateway paymentGateway,
                      OrquestrationGateway orquestrationGateway) throws OrderCreationException, OrderCancellationException;

    void completeOrder(CustomQueueMessage<CreateOrderResponse> response,
                       OrderGateway orderGateway,
                       OrquestrationGateway orquestrationGateway) throws OrderCreationException;

    void notifyCustomer(CustomQueueMessage<CreateOrderResponse> response,
                        CustomerGateway customerGateway,
                        OrquestrationGateway orquestrationGateway,
                        OrquestrationStepEnum step) throws OrderCreationException;

}
