package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;

public interface OrderGateway {

    void commandOrderCreation(CustomQueueMessage<String> message);

    void commandOrderPreparation(CustomQueueMessage<String> message);

    void commandOrderCompletion(CustomQueueMessage<String> message);

    void commandOrderCancellation(CustomQueueMessage<String> message);
}
