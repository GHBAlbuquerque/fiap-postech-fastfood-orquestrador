package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Order;

public interface OrderCreationOrquestratorUseCase {

    void createOrder(Order order,
                     OrderGateway orderGateway,
                     OrquestrationGateway orquestrationGateway);

    void createPayment(PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway);

    void chargePayment(PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway);

    void prepareOrder(OrderGateway orderGateway,
                      OrquestrationGateway orquestrationGateway);

    void completeOrder(OrderGateway orderGateway,
                       OrquestrationGateway orquestrationGateway);

    void notifyCustomer(CustomerGateway customerGateway,
                        OrquestrationGateway orquestrationGateway);

}
