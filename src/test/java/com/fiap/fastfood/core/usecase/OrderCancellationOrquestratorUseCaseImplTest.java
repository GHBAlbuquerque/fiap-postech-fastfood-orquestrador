package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrderGateway;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fiap.fastfood.core.entity.OrquestrationStepEnum.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCancellationOrquestratorUseCaseImplTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private OrquestrationGateway orquestrationGateway;

    @InjectMocks
    private OrderCancellationOrquestratorUseCaseImpl orquestratorUseCase;


    @Test
    void orquestrate_reversePayment_success() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(PREPARE_ORDER);

        orquestratorUseCase.orquestrate(message, orderGateway, paymentGateway, orquestrationGateway);

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.REVERSE_PAYMENT.name()), any());
        verify(paymentGateway, times(1)).commandPaymentReversal(any(CustomQueueMessage.class));
    }


    @Test
    void orquestrate_cancelPayment_success() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(CANCEL_PAYMENT);
        orquestratorUseCase.orquestrate(message, orderGateway, paymentGateway, orquestrationGateway);

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.CANCEL_ORDER.name()), any());
    }

    @Test
    void orquestrate_cancelOrder_success() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(CANCEL_ORDER);

        orquestratorUseCase.orquestrate(message, orderGateway, paymentGateway, orquestrationGateway);

        verify(orderGateway, times(0)).commandOrderCancellation(any());
    }

    @Test
    void reversePayment_success() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(REVERSE_PAYMENT);

        orquestratorUseCase.reversePayment(message, paymentGateway, orquestrationGateway);

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.REVERSE_PAYMENT.name()), any());
        verify(paymentGateway, times(1)).commandPaymentReversal(any(CustomQueueMessage.class));
    }

    @Test
    void reversePayment_failure() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(REVERSE_PAYMENT);

        doThrow(new RuntimeException("Payment reversal failed")).when(paymentGateway).commandPaymentReversal(any());

        assertThrows(OrderCancellationException.class, () ->
                orquestratorUseCase.reversePayment(message, paymentGateway, orquestrationGateway));

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.REVERSE_PAYMENT.name()), any());
        verify(paymentGateway, times(1)).commandPaymentReversal(any());
    }

    @Test
    void cancelPayment_success() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(CANCEL_PAYMENT);

        orquestratorUseCase.cancelPayment(message, paymentGateway, orquestrationGateway);

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.CANCEL_PAYMENT.name()), any());
        verify(paymentGateway, times(1)).commandPaymentCancellation(any(CustomQueueMessage.class));
    }

    @Test
    void cancelPayment_failure() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(CANCEL_PAYMENT);

        doThrow(new RuntimeException("Payment cancellation failed")).when(paymentGateway).commandPaymentCancellation(any());

        assertThrows(OrderCancellationException.class, () ->
                orquestratorUseCase.cancelPayment(message, paymentGateway, orquestrationGateway));

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.CANCEL_PAYMENT.name()), any());
        verify(paymentGateway, times(1)).commandPaymentCancellation(any());
    }

    @Test
    void cancelOrder_success() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(CANCEL_ORDER);

        orquestratorUseCase.cancelOrder(message, orderGateway, orquestrationGateway);

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.CANCEL_ORDER.name()), any());
        verify(orderGateway, times(1)).commandOrderCancellation(any(CustomQueueMessage.class));
    }

    @Test
    void cancelOrder_failure() throws OrderCancellationException, OrderCreationException {
        final var message = getCreateOrderResponseCustomQueueMessage(CANCEL_ORDER);

        doThrow(new RuntimeException("Order cancellation failed")).when(orderGateway).commandOrderCancellation(any());

        assertThrows(OrderCancellationException.class, () ->
                orquestratorUseCase.cancelOrder(message, orderGateway, orquestrationGateway));

        verify(orquestrationGateway, times(1)).updateStepRecord(any(), eq(OrquestrationStepEnum.CANCEL_ORDER.name()), any());
        verify(orderGateway, times(1)).commandOrderCancellation(any());
    }


    private static CustomQueueMessage<CreateOrderResponse> getCreateOrderResponseCustomQueueMessage(OrquestrationStepEnum step) {
        final var headers = new CustomMessageHeaders("sagaId", "orderId", "messageType", "source");
        final var body = new CreateOrderResponse("orderId", 1L, "paymentId", step, true);
        return new CustomQueueMessage<>(headers, body);
    }

}
