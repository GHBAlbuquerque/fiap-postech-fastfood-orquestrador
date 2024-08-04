package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GatewayBeanDeclarationTest {

    @InjectMocks
    private GatewayBeanDeclaration declaration;

    @Test
    void PaymentGatewayTest() {
        final var mock = Mockito.mock(PaymentRepository.class);

        final var result = declaration.paymentGateway(mock);

        Assertions.assertNotNull(result);
    }


}
