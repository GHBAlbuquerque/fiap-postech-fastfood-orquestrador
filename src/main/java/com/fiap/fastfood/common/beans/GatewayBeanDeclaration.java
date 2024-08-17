package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.*;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import com.fiap.fastfood.communication.gateways.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public OrderGateway orderGateway(MessageSender messageSender) {
        return new OrderGatewayImpl(messageSender);
    }

    @Bean
    public PaymentGateway productGateway(MessageSender messageSender) {
        return new PaymentGatewayImpl(messageSender);
    }

    @Bean
    public CustomerGateway customerGateway(MessageSender messageSender) {
        return new CustomerGatewayImpl(messageSender);
    }

    @Bean
    public OrquestrationGateway orquestrationGateway(OrquestrationRepository orquestrationRepository) {
        return new OrquestrationGatewayImpl(orquestrationRepository);
    }

    @Bean
    public ResponseGateway responseGateway(OrderCreationOrquestratorUseCase orderCreationOrquestratorUseCase, OrderCancellationOrquestratorUseCase orderCancellationOrquestratorUseCase, OrderGateway orderGateway, PaymentGateway paymentGateway, CustomerGateway customerGateway, OrquestrationGateway orquestrationGateway) {
        return new ResponseGatewayImpl(orderCreationOrquestratorUseCase, orderCancellationOrquestratorUseCase, orderGateway, paymentGateway, customerGateway, orquestrationGateway);
    }
}
