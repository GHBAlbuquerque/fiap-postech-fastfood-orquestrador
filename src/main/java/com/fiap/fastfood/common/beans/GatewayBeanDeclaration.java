package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.communication.gateways.CustomerGatewayImpl;
import com.fiap.fastfood.communication.gateways.OrderGatewayImpl;
import com.fiap.fastfood.communication.gateways.PaymentGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public OrderGateway orderGateway() {
        return new OrderGatewayImpl();
    }

    @Bean
    public PaymentGateway productGateway() {
        return new PaymentGatewayImpl();
    }

    @Bean
    public CustomerGateway customerGateway() {
        return new CustomerGatewayImpl();
    }


}
