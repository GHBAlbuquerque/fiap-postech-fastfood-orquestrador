package com.fiap.fastfood.core.usecase;

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
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCreationOrquestratorUseCaseImplTest {

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
    private OrderCreationOrquestratorUseCaseImpl orderCreationOrquestratorUseCaseImpl;

    @Test
    void testOrquestrateCreateOrder() throws OrderCreationException, OrderCancellationException {
        final var executedStep = OrquestrationStepEnum.CREATE_ORDER;
        final var message = getCreateOrderResponseCustomQueueMessage(executedStep);
        final var sagaId = message.getHeaders().getSagaId();

        orderCreationOrquestratorUseCaseImpl.orquestrate(message, orderGateway, paymentGateway, customerGateway, orquestrationGateway);

        verify(paymentGateway).commandPaymentCreation(any(CustomQueueMessage.class));
        verify(orquestrationGateway).updateStepRecord(any(), eq(OrquestrationStepEnum.CREATE_PAYMENT.name()), anyString());
        verify(orderGateway, never()).commandOrderCompletion(any(CustomQueueMessage.class));
    }

    @Test
    void testCreateOrderSuccess() throws OrderCreationException {
        final var order = new Order();
        final var stepName = OrquestrationStepEnum.CREATE_ORDER.name();

        final var messageCaptor = ArgumentCaptor.forClass(CustomQueueMessage.class);

        when(orquestrationGateway.createStepRecord(stepName)).thenReturn("id-123");

        orderCreationOrquestratorUseCaseImpl.createOrder(order, orderGateway, orquestrationGateway);

        verify(orquestrationGateway).createStepRecord(stepName);
        verify(orderGateway).commandOrderCreation(messageCaptor.capture());

        final var capturedMessage = messageCaptor.getValue();
        assertEquals("id-123", capturedMessage.getHeaders().getSagaId());
    }

    @Test
    void testCreatePaymentSuccess() throws OrderCreationException, OrderCancellationException {
        final var message = getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum.CREATE_ORDER);
        final var sagaId = message.getHeaders().getSagaId();
        final var orderId = message.getHeaders().getOrderId();

        orderCreationOrquestratorUseCaseImpl.createPayment(message, paymentGateway, orderGateway, orquestrationGateway);

        verify(paymentGateway).commandPaymentCreation(any(CustomQueueMessage.class));
        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.CREATE_PAYMENT.name(), orderId);
    }

    @Test
    void testChargePaymentSuccess() throws OrderCreationException, OrderCancellationException {
        final var message = getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum.CREATE_ORDER);
        final var sagaId = message.getHeaders().getSagaId();
        final var orderId = message.getHeaders().getOrderId();

        orderCreationOrquestratorUseCaseImpl.chargePayment(message, paymentGateway, orquestrationGateway);

        verify(paymentGateway).commandPaymentCharge(any(CustomQueueMessage.class));
        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.CHARGE_PAYMENT.name(), orderId);
    }

    @Test
    void testPrepareOrderSuccess() throws OrderCreationException, OrderCancellationException {
        final var message = getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum.CREATE_ORDER);
        final var sagaId = message.getHeaders().getSagaId();
        final var orderId = message.getHeaders().getOrderId();

        orderCreationOrquestratorUseCaseImpl.prepareOrder(message, orderGateway, paymentGateway, orquestrationGateway);

        verify(orderGateway).commandOrderPreparation(any(CustomQueueMessage.class));
        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.PREPARE_ORDER.name(), orderId);
    }

    @Test
    void testCompleteOrderSuccess() throws OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum.CREATE_ORDER);
        final var sagaId = message.getHeaders().getSagaId();
        final var orderId = message.getHeaders().getOrderId();

        orderCreationOrquestratorUseCaseImpl.completeOrder(message, orderGateway, orquestrationGateway);

        verify(orderGateway).commandOrderCompletion(any(CustomQueueMessage.class));
        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.COMPLETE_ORDER.name(), orderId);
    }

    @Test
    void testNotifyCustomerSuccess() throws OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum.CREATE_ORDER);
        final var sagaId = message.getHeaders().getSagaId();
        final var orderId = message.getHeaders().getOrderId();

        orderCreationOrquestratorUseCaseImpl.notifyCustomer(message, customerGateway, orquestrationGateway, OrquestrationStepEnum.NOTIFY_CUSTOMER);

        verify(customerGateway).commandCustomerNotification(any(CustomQueueMessage.class));
        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.NOTIFY_CUSTOMER.name(), orderId);
    }

    private static CustomQueueMessage<CreateOrderResponse> getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum step) {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new CreateOrderResponse("orderId", 1L, "paymentId", step, true);
        return new CustomQueueMessage<>(headers, body);
    }

}
