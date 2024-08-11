package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.external.orm.OrquestrationORM;

import java.util.UUID;

public class OrquestrationGatewayImpl implements OrquestrationGateway {

    private final OrquestrationRepository repository;

    public OrquestrationGatewayImpl(OrquestrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createStepRecord(String stepName) {
        final var id = UUID.randomUUID().toString();

        final var orm = new OrquestrationORM(id, stepName);
        final var record = repository.save(orm);

        return record.getId();
    }

    @Override
    public String saveStepRecord(String id, String orderId, String stepName) {
        final var orm = new OrquestrationORM(id, orderId, stepName);
        final var record = repository.save(orm);

        return record.getId();
    }
}
