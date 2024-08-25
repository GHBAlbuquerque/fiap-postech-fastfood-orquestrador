package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.MessageCreationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderGatewayImplTest {

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private OrderGatewayImpl orderGateway;

    @BeforeEach
    void setUp() {
        // Set up the queue URLs via ReflectionTestUtils
        ReflectionTestUtils.setField(orderGateway, "queueCreateOrder", "queueCreateOrder");
        ReflectionTestUtils.setField(orderGateway, "queuePrepareOrder", "queuePrepareOrder");
        ReflectionTestUtils.setField(orderGateway, "queueCompleteOrder", "queueCompleteOrder");
        ReflectionTestUtils.setField(orderGateway, "queueCancelOrder", "queueCancelOrder");
    }

    @Test
    void commandOrderCreationSuccess() throws OrderCreationException, MessageCreationException {
        final var message = getCreateOrderMessage();

        orderGateway.commandOrderCreation(message);

        verify(messageSender, times(1)).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );
    }

    @Test
    void commandOrderPreparationSuccess() throws OrderCreationException, MessageCreationException {
        final var message = getMessage();

        orderGateway.commandOrderPreparation(message);

        verify(messageSender, times(1)).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );
    }

    @Test
    void commandOrderCompletionSuccess() throws OrderCreationException, MessageCreationException {
        final var message = getMessage();

        orderGateway.commandOrderCompletion(message);

        verify(messageSender, times(1)).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );
    }

    @Test
    void commandOrderCancellationSuccess() throws OrderCancellationException, MessageCreationException {
        final var message = getMessage();

        orderGateway.commandOrderCancellation(message);

        verify(messageSender, times(1)).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );
    }

    @Test
    void commandOrderCreationFailure() throws MessageCreationException {
        final var message = getCreateOrderMessage();

        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );

        assertThrows(OrderCreationException.class, () -> orderGateway.commandOrderCreation(message));
    }

    @Test
    void commandOrderPreparationFailure() throws MessageCreationException {
        final var message = getMessage();

        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );

        assertThrows(OrderCreationException.class, () -> orderGateway.commandOrderPreparation(message));
    }

    @Test
    void commandOrderCompletionFailure() throws MessageCreationException {
        final var message = getMessage();

        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );

        assertThrows(OrderCreationException.class, () -> orderGateway.commandOrderCompletion(message));
    }

    @Test
    void commandOrderCancellationFailure() throws MessageCreationException {
        final var message = getMessage();

        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(
                eq(message),
                eq(message.getHeaders().getSagaId()),
                anyString()
        );

        assertThrows(OrderCancellationException.class, () -> orderGateway.commandOrderCancellation(message));
    }

    private static CustomQueueMessage<CreateOrderCommand> getCreateOrderMessage() {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new CreateOrderCommand(1L, List.of());
        return new CustomQueueMessage<>(headers, body);
    }

    private static CustomQueueMessage<OrderCommand> getMessage() {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new OrderCommand("orderId", 1L, "paymentId");
        return new CustomQueueMessage<>(headers, body);
    }
}
