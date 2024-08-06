package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;

public interface ResponseGateway {

    void listenToCreateOrderResponse(CustomQueueMessage<String> message);

}
