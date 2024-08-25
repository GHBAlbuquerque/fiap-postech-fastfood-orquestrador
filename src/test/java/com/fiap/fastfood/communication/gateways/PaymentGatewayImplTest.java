package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.PaymentCommand;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentGatewayImplTest {

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private PaymentGatewayImpl paymentGateway;


    @BeforeEach
    void setUp() {
        // Set up the queue URLs via ReflectionTestUtils
        ReflectionTestUtils.setField(paymentGateway, "queuePaymentCreation", "queuePaymentCreationURL");
        ReflectionTestUtils.setField(paymentGateway, "queuePaymentChage", "queuePaymentChageURL");
        ReflectionTestUtils.setField(paymentGateway, "queuePaymentReversal", "queuePaymentReversalURL");
        ReflectionTestUtils.setField(paymentGateway, "queuePaymentCancellation", "queuePaymentCancellationURL");
    }

    // Testes de Sucesso

    @Test
    void testCommandPaymentCreationSuccess() throws Exception {
        paymentGateway.commandPaymentCreation(getMessage());
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    @Test
    void testCommandPaymentChargeSuccess() throws Exception {
        paymentGateway.commandPaymentCharge(getMessage());
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    @Test
    void testCommandPaymentReversalSuccess() throws Exception {
        paymentGateway.commandPaymentReversal(getMessage());
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    @Test
    void testCommandPaymentCancellationSuccess() throws Exception {
        paymentGateway.commandPaymentCancellation(getMessage());
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    // Testes de Falha

    @Test
    void testCommandPaymentCreationFailure() throws MessageCreationException {
        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(any(), anyString(), anyString());

        assertThrows(OrderCreationException.class, () -> paymentGateway.commandPaymentCreation(getMessage()));
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    @Test
    void testCommandPaymentChargeFailure() throws MessageCreationException {
        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(any(), anyString(), anyString());

        assertThrows(OrderCreationException.class, () -> paymentGateway.commandPaymentCharge(getMessage()));
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    @Test
    void testCommandPaymentReversalFailure() throws MessageCreationException {
        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(any(), anyString(), anyString());

        assertThrows(OrderCancellationException.class, () -> paymentGateway.commandPaymentReversal(getMessage()));
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    @Test
    void testCommandPaymentCancellationFailure() throws MessageCreationException {
        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(any(), anyString(), anyString());

        assertThrows(OrderCancellationException.class, () -> paymentGateway.commandPaymentCancellation(getMessage()));
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    private static CustomQueueMessage<PaymentCommand> getMessage() {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new PaymentCommand("orderId", 1L, "paymentId");
        return new CustomQueueMessage<>(headers, body);
    }
}
