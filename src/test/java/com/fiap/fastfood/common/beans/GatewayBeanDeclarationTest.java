package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GatewayBeanDeclarationTest {

    @InjectMocks
    private GatewayBeanDeclaration declaration;

    @Test
    void OrderGatewayTest() {
        final var mock = Mockito.mock(MessageSender.class);

        final var result = declaration.orderGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void PaymentGatewayTest() {
        final var mock = Mockito.mock(MessageSender.class);

        final var result = declaration.paymentGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void CustomerGatewayTest() {
        final var mock = Mockito.mock(MessageSender.class);

        final var result = declaration.customerGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void OrquestrationGatewayTest() {
        final var mock = Mockito.mock(OrquestrationRepository.class);

        final var result = declaration.orquestrationGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void ResponseGatewayTest() {
        final var mockUseCase = Mockito.mock(OrderCreationOrquestratorUseCase.class);
        final var mockUseCaseCancel = Mockito.mock(OrderCancellationOrquestratorUseCase.class);
        final var mockOrder = Mockito.mock(OrderGateway.class);
        final var mockPayment = Mockito.mock(PaymentGateway.class);
        final var mockCustomer = Mockito.mock(CustomerGateway.class);
        final var mockOrquestration = Mockito.mock(OrquestrationGateway.class);

        final var result = declaration.responseGateway(mockUseCase, mockUseCaseCancel, mockOrder, mockPayment, mockCustomer, mockOrquestration);

        Assertions.assertNotNull(result);
    }


}
