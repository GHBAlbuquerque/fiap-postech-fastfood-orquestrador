package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.CustomQueueMessage;

public interface CustomerGateway {

    void commandCustomerNotification(CustomQueueMessage<String> message);

}
