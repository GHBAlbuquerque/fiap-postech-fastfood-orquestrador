package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.dto.response.CreateOrderResponse;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import org.springframework.messaging.MessageHeaders;

public interface ResponseGateway {

    void listenToCreateOrderResponse(MessageHeaders headers, CustomQueueMessage<CreateOrderResponse> message) throws OrderCreationException;

}
