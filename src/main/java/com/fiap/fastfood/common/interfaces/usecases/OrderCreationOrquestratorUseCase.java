package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Order;

public interface OrderCreationOrquestratorUseCase {

    void createOrder(Order order, OrderGateway orderGateway);

    void prepareOrder(OrderGateway orderGateway);

    void completeOrder(OrderGateway orderGateway);

    void notifyCustomer(CustomerGateway customerGateway);

    void createPayment(PaymentGateway paymentGateway);

    void chargePayment(PaymentGateway paymentGateway);
}
