package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;

public interface OrderCancellationOrquestratorUseCase {

    void cancelOrder(OrderGateway orderGateway);

    void cancelPayment(PaymentGateway paymentGateway);

    void reversePayment(PaymentGateway paymentGateway);
}
