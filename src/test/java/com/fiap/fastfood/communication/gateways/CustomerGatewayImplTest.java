package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.NotifyCustomerCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.MessageCreationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
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
class CustomerGatewayImplTest {

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private CustomerGatewayImpl customerGateway;

    @BeforeEach
    void setUp() {
        // Set up the queue URL via ReflectionTestUtils
        ReflectionTestUtils.setField(customerGateway, "queueCustomerNotification", "queueCustomerNotificationURL");
    }

    // Teste de Sucesso
    @Test
    void testCommandCustomerNotificationSuccess() throws Exception {
        customerGateway.commandCustomerNotification(getMessage());
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    // Teste de Falha
    @Test
    void testCommandCustomerNotificationFailure() throws MessageCreationException {
        doThrow(new RuntimeException("Test Exception")).when(messageSender).sendMessage(any(), anyString(), anyString());

        assertThrows(OrderCreationException.class, () -> customerGateway.commandCustomerNotification(getMessage()));
        verify(messageSender, times(1)).sendMessage(any(), anyString(), anyString());
    }

    private static CustomQueueMessage<NotifyCustomerCommand> getMessage() {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new NotifyCustomerCommand("orderId", 1L, "paymentId", OrquestrationStepEnum.CREATE_ORDER);
        return new CustomQueueMessage<>(headers, body);
    }
}
