package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCaseBeanDeclarationTest {

    @InjectMocks
    private UseCaseBeanDeclaration declaration;

    @Test
    void orderCreationOrquestratorUseCaseTest() {
        final var mock = Mockito.mock(OrderCancellationOrquestratorUseCase.class);

        final var result = declaration.orderCreationOrquestratorUseCase(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void orderCancellationOrquestratorUseCaseTest() {
        final var result = declaration.orderCancellationOrquestratorUseCase();

        Assertions.assertNotNull(result);
    }
}


