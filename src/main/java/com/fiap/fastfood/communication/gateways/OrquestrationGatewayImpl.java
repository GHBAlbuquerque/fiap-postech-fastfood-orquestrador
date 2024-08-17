package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.external.orm.OrquestrationRecordORM;

import java.util.UUID;

public class OrquestrationGatewayImpl implements OrquestrationGateway {

    private final OrquestrationRepository repository;

    public OrquestrationGatewayImpl(OrquestrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createStepRecord(String stepId) {
        final var id = UUID.randomUUID().toString();

        final var orm = new OrquestrationRecordORM(id, stepId);
        final var record = repository.save(orm);

        return record.getSagaId();
    }

    @Override
    public String saveStepRecord(String id, String stepId, String orderId) {
        final var orm = new OrquestrationRecordORM(id, stepId, orderId);
        final var record = repository.save(orm);

        return record.getSagaId();
    }
}
