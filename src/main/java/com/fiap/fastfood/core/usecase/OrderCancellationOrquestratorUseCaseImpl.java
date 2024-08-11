package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;

public class OrderCancellationOrquestratorUseCaseImpl implements OrderCancellationOrquestratorUseCase {

    @Override
    public void cancelOrder(OrderGateway orderGateway, OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void cancelPayment(PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {

    }

    @Override
    public void reversePayment(PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {

    }
}
