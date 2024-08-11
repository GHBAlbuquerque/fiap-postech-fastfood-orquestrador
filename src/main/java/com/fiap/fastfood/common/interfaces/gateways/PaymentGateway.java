package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.command.PaymentCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;

public interface PaymentGateway {

    void commandPaymentCreation(CustomQueueMessage<PaymentCommand> message) throws OrderCreationException;

    void commandPaymentCharge(CustomQueueMessage<PaymentCommand> message) throws OrderCreationException;

    void commandPaymentReversal(CustomQueueMessage<PaymentCommand> message) throws OrderCancellationException;

    void commandPaymentCancellation(CustomQueueMessage<PaymentCommand> message) throws OrderCancellationException;

}
