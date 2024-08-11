package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.communication.gateways.CustomerGatewayImpl;
import com.fiap.fastfood.communication.gateways.OrderGatewayImpl;
import com.fiap.fastfood.communication.gateways.OrquestrationGatewayImpl;
import com.fiap.fastfood.communication.gateways.PaymentGatewayImpl;
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

}
