package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.communication.gateways.PaymentGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public PaymentGateway paymentGateway(PaymentRepository repository) {
        return new PaymentGatewayImpl(repository);
    }
}
