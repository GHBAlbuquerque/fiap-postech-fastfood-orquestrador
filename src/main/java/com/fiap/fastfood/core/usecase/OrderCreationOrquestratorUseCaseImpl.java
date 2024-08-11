package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import com.fiap.fastfood.core.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderCreationOrquestratorUseCaseImpl implements OrderCreationOrquestratorUseCase {

    private static final Logger logger = LogManager.getLogger(OrderCreationOrquestratorUseCaseImpl.class);

    @Override
    public void createOrder(Order order, OrderGateway orderGateway) {


    }

    @Override
    public void prepareOrder(OrderGateway orderGateway) {

    }

    @Override
    public void completeOrder(OrderGateway orderGateway) {

    }

    @Override
    public void notifyCustomer(CustomerGateway customerGateway) {

    }

    @Override
    public void createPayment(PaymentGateway paymentGateway) {

    }

    @Override
    public void chargePayment(PaymentGateway paymentGateway) {

    }
}
