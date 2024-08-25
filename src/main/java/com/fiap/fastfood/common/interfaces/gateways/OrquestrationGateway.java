package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;

public interface OrquestrationGateway {

    String createStepRecord(String stepId);

    String updateStepRecord(String id, String stepId, String orderId) throws OrderCreationException;
}
