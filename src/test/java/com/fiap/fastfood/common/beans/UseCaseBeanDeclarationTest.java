package com.fiap.fastfood.common.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UseCaseBeanDeclarationTest {

    @InjectMocks
    private UseCaseBeanDeclaration declaration;

    @Test
    void paymentUseCaseTest() {
        final var result = declaration.paymentUseCase();

        Assertions.assertNotNull(result);
    }
}
