package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.external.orm.OrquestrationRecordORM;

import java.util.UUID;

import static com.fiap.fastfood.common.exceptions.custom.ExceptionCodes.SAGA_13_ORQUESTRATION_RECORD_404;

public class OrquestrationGatewayImpl implements OrquestrationGateway {

    private final OrquestrationRepository repository;

    public OrquestrationGatewayImpl(OrquestrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createStepRecord(String stepId) {
        final var sagaId = UUID.randomUUID().toString();

        final var orm = new OrquestrationRecordORM(sagaId, stepId);
        final var orquestrationRecord = repository.save(orm);

        return orquestrationRecord.getSagaId();
    }

    @Override
    public String updateStepRecord(String id, String stepId, String orderId)
            throws OrderCreationException {
        final var optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new OrderCreationException(SAGA_13_ORQUESTRATION_RECORD_404, "Orquestration Record not found.");
        }

        final var orm = optional.get();

        orm.setStepId(stepId);
        orm.setOrderId(orderId);

        final var orquestrationRecord = repository.save(orm);

        return orquestrationRecord.getSagaId();
    }
}
