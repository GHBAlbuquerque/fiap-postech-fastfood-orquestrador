package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import com.fiap.fastfood.core.usecase.OrderCancellationOrquestratorUseCaseImpl;
import com.fiap.fastfood.core.usecase.OrderCreationOrquestratorUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanDeclaration {

    @Bean
    public OrderCreationOrquestratorUseCase orderCreationOrquestratorUseCase(
            OrderCancellationOrquestratorUseCase orderCancellationOrquestratorUseCase
    ) {
        return new OrderCreationOrquestratorUseCaseImpl(orderCancellationOrquestratorUseCase);
    }

    @Bean
    public OrderCancellationOrquestratorUseCase orderCancellationOrquestratorUseCase() {
        return new OrderCancellationOrquestratorUseCaseImpl();
    }
}
