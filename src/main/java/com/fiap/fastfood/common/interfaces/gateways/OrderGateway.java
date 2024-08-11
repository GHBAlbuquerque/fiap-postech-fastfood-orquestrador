package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;

public interface OrderGateway {

    void commandOrderCreation(CustomQueueMessage<String> message) throws OrderCreationException;

    void commandOrderPreparation(CustomQueueMessage<String> message) throws OrderCreationException;

    void commandOrderCompletion(CustomQueueMessage<String> message) throws OrderCreationException;

    void commandOrderCancellation(CustomQueueMessage<String> message) throws OrderCancellationException;
}
