package com.fiap.fastfood.common.interfaces.gateways;

public interface OrquestrationGateway {

    String createStepRecord(String stepName);

    String saveStepRecord(String id, String orderId, String stepName);
}
