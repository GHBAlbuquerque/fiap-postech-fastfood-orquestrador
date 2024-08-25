package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.CustomerGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecases.OrderCancellationOrquestratorUseCase;
import com.fiap.fastfood.common.interfaces.usecases.OrderCreationOrquestratorUseCase;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageHeaders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResponseGatewayImplTest {

    @Mock
    private OrderCreationOrquestratorUseCase orderCreationOrquestratorUseCase;

    @Mock
    private OrderCancellationOrquestratorUseCase orderCancellationOrquestratorUseCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private OrquestrationGateway orquestrationGateway;

    @Mock
    private Logger logger;

    @InjectMocks
    private ResponseGatewayImpl responseGateway;

    @Test
    void listenToCreateOrderResponse_StepSuccessful_ShouldCallOrderCreationOrquestration() throws OrderCreationException, OrderCancellationException {
        // Arrange
        final var headers = mock(MessageHeaders.class);
        final var createOrderResponse = mock(CreateOrderResponse.class);
        when(createOrderResponse.getStepSuccessful()).thenReturn(true);
        final var message = getMessage(createOrderResponse);

        // Act
        responseGateway.listenToCreateOrderResponse(headers, message);

        // Assert
        verify(orderCreationOrquestratorUseCase).orquestrate(message, orderGateway, paymentGateway, customerGateway, orquestrationGateway);
        verify(orderCancellationOrquestratorUseCase, never()).orquestrate(any(), any(), any(), any());
    }


    @Test
    void listenToCreateOrderResponse_StepNotSuccessful_ShouldCallOrderCancellationOrquestration() throws OrderCreationException, OrderCancellationException {
        // Arrange
        final var headers = mock(MessageHeaders.class);
        final var createOrderResponse = mock(CreateOrderResponse.class);
        when(createOrderResponse.getStepSuccessful()).thenReturn(false);
        final var message = getMessage(createOrderResponse);

        // Act
        responseGateway.listenToCreateOrderResponse(headers, message);

        // Assert
        verify(orderCancellationOrquestratorUseCase).orquestrate(message, orderGateway, paymentGateway, orquestrationGateway);
        verify(orderCreationOrquestratorUseCase, never()).orquestrate(any(), any(), any(), any(), any());
    }

    @Test
    void listenToCreateOrderResponse_ShouldThrowOrderCreationException_OnException() throws OrderCreationException, OrderCancellationException {
        // Arrange
        final var headers = mock(MessageHeaders.class);
        final var createOrderResponse = mock(CreateOrderResponse.class);
        when(createOrderResponse.getStepSuccessful()).thenReturn(true);
        final var message = getMessage(createOrderResponse);

        doThrow(new RuntimeException("Test exception")).when(orderCreationOrquestratorUseCase).orquestrate(any(), any(), any(), any(), any());

        // Act & Assert
        assertThrows(OrderCreationException.class, () -> responseGateway.listenToCreateOrderResponse(headers, message));
    }

    private static CustomQueueMessage<CreateOrderResponse> getMessage(CreateOrderResponse createOrderResponse) {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        return new CustomQueueMessage<CreateOrderResponse>(headers, createOrderResponse);
    }
}
