package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;

public interface PaymentGateway {

    void commandPaymentCreation(CustomQueueMessage<String> message) throws OrderCreationException;

    void commandPaymentCharge(CustomQueueMessage<String> message) throws OrderCreationException;

    void commandPaymentReversal(CustomQueueMessage<String> message) throws OrderCancellationException;

    void commandPaymentCancellation(CustomQueueMessage<String> message) throws OrderCancellationException;

}
