package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.command.CreateOrderCommand;
import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;

public interface OrderGateway {

    void commandOrderCreation(CustomQueueMessage<CreateOrderCommand> message) throws OrderCreationException;

    void commandOrderPreparation(CustomQueueMessage<OrderCommand> message) throws OrderCreationException;

    void commandOrderCompletion(CustomQueueMessage<OrderCommand> message) throws OrderCreationException;

    void commandOrderCancellation(CustomQueueMessage<OrderCommand> message) throws OrderCancellationException;
}
