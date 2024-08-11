package com.fiap.fastfood.common.interfaces.usecases;

import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;

public interface OrderCancellationOrquestratorUseCase {

    void reversePayment(PaymentGateway paymentGateway,
                        OrquestrationGateway orquestrationGateway);

    void cancelPayment(PaymentGateway paymentGateway,
                       OrquestrationGateway orquestrationGateway);

    void cancelOrder(OrderGateway orderGateway,
                     OrquestrationGateway orquestrationGateway);
}
