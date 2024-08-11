package com.fiap.fastfood.common.interfaces.gateways;

public interface OrquestrationGateway {

    String createStepRecord(String stepId);

    String saveStepRecord(String id, String stepId, String orderId);
}
