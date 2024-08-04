package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.core.usecase.PaymentUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanDeclaration {

    @Bean
    public PaymentUseCase paymentUseCase() {
        return new PaymentUseCaseImpl();
    }
}
