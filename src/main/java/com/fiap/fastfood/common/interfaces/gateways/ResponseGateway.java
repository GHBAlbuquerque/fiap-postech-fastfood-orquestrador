package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;

public interface ResponseGateway {

    void listenToCreateOrderResponse(CustomQueueMessage<String> message) throws OrderCreationException;

}
