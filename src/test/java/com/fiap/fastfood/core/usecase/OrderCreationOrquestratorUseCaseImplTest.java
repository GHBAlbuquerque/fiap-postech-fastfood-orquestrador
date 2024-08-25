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
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.Order;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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


    @Test
    void testCreateOrder_Failure_ThrowsOrderCreationException() throws OrderCreationException {
        doThrow(new RuntimeException("Database error")).when(orderGateway).commandOrderCreation(any());

        assertThrows(OrderCreationException.class, () -> orderCreationOrquestratorUseCaseImpl.createOrder(new Order(1L, List.of()), orderGateway, orquestrationGateway));

        verify(orquestrationGateway).createStepRecord(any());
    }

    @Test
    void testCreatePayment_Failure_ThrowsOrderCreationException() throws OrderCreationException {
        TransactionInformationStorage.putReceiveCount("1");

        final var response = getCompleteMessage(OrquestrationStepEnum.CREATE_ORDER);
        final var sagaId = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();

        doThrow(new RuntimeException("Payment service unavailable")).when(paymentGateway).commandPaymentCreation(any());

        assertThrows(OrderCreationException.class, () -> orderCreationOrquestratorUseCaseImpl.createPayment(response, paymentGateway, orderGateway, orquestrationGateway));

        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.CREATE_PAYMENT.name(), orderId);
    }

    @Test
    void testChargePayment_Failure_ThrowsOrderCreationException() throws OrderCreationException {
        TransactionInformationStorage.putReceiveCount("1");

        final var response = getCompleteMessage(OrquestrationStepEnum.CREATE_PAYMENT);
        final var sagaId = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();

        doThrow(new RuntimeException("Payment charge failed")).when(paymentGateway).commandPaymentCharge(any());

        assertThrows(OrderCreationException.class, () -> orderCreationOrquestratorUseCaseImpl.chargePayment(response, paymentGateway, orquestrationGateway));

        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.CHARGE_PAYMENT.name(), orderId);
    }

    @Test
    void testPrepareOrder_Failure_ThrowsOrderCreationException() throws OrderCreationException {
        TransactionInformationStorage.putReceiveCount("1");

        final var response = getCompleteMessage(OrquestrationStepEnum.CHARGE_PAYMENT);
        final var sagaId = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();

        doThrow(new RuntimeException("Order preparation failed")).when(orderGateway).commandOrderPreparation(any());

        assertThrows(OrderCreationException.class, () -> orderCreationOrquestratorUseCaseImpl.prepareOrder(response, orderGateway, paymentGateway, orquestrationGateway));

        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.PREPARE_ORDER.name(), orderId);
    }

    @Test
    void testCompleteOrder_Failure_ThrowsOrderCreationException() throws OrderCreationException {
        TransactionInformationStorage.putReceiveCount("1");

        final var response = getCompleteMessage(OrquestrationStepEnum.PREPARE_ORDER);
        final var sagaId = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();

        doThrow(new RuntimeException("Order completion failed")).when(orderGateway).commandOrderCompletion(any());

        assertThrows(OrderCreationException.class, () -> orderCreationOrquestratorUseCaseImpl.completeOrder(response, orderGateway, orquestrationGateway));

        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.COMPLETE_ORDER.name(), orderId);
    }

    @Test
    void testNotifyCustomer_Failure_ThrowsOrderCreationException() throws OrderCreationException {
        TransactionInformationStorage.putReceiveCount("1");

        final var response = getCompleteMessage(OrquestrationStepEnum.CHARGE_PAYMENT);
        final var sagaId = response.getHeaders().getSagaId();
        final var orderId = response.getHeaders().getOrderId();

        doThrow(new RuntimeException("Customer notification failed")).when(customerGateway).commandCustomerNotification(any());

        assertThrows(OrderCreationException.class, () -> orderCreationOrquestratorUseCaseImpl.notifyCustomer(response, customerGateway, orquestrationGateway, OrquestrationStepEnum.NOTIFY_CUSTOMER));

        verify(orquestrationGateway).updateStepRecord(sagaId, OrquestrationStepEnum.NOTIFY_CUSTOMER.name(), orderId);
    }

    private static CustomQueueMessage<CreateOrderResponse> getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum step) {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new CreateOrderResponse("orderId", 1L, "paymentId", step, true);
        return new CustomQueueMessage<>(headers, body);
    }

    private static CustomQueueMessage<CreateOrderResponse> getCompleteMessage(OrquestrationStepEnum step) {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new CreateOrderResponse("orderId", 1L, "paymentId", step, true);
        return new CustomQueueMessage<CreateOrderResponse>(headers, body);
    }

}
