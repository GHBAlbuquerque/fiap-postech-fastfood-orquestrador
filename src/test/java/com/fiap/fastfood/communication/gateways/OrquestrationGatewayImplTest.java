package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.interfaces.datasources.OrquestrationRepository;
import com.fiap.fastfood.external.orm.OrquestrationRecordORM;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrquestrationGatewayImplTest {

    @Mock
    private OrquestrationRepository repository;

    @InjectMocks
    private OrquestrationGatewayImpl orquestrationGateway;

    @Test
    void createStepRecordSuccess() {
        final var stepId = "step-id";
        final var sagaId = UUID.randomUUID().toString();
        final var orm = new OrquestrationRecordORM(sagaId, stepId);

        when(repository.save(any(OrquestrationRecordORM.class))).thenReturn(orm);

        final var result = orquestrationGateway.createStepRecord(stepId);

        assertEquals(sagaId, result);
        verify(repository, times(1)).save(any(OrquestrationRecordORM.class));
    }

    @Test
    void updateStepRecordSuccess() throws Exception {
        final var id = "record-id";
        final var stepId = "step-id";
        final var orderId = "order-id";
        final var orm = new OrquestrationRecordORM(UUID.randomUUID().toString(), stepId);

        when(repository.findById(id)).thenReturn(Optional.of(orm));
        when(repository.save(any(OrquestrationRecordORM.class))).thenReturn(orm);

        final var result = orquestrationGateway.updateStepRecord(id, stepId, orderId);

        assertEquals(orm.getSagaId(), result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(orm);
    }

    @Test
    void updateStepRecordFailureRecordNotFound() {
        final var id = "non-existent-id";
        final var stepId = "step-id";
        final var orderId = "order-id";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderCreationException.class, () ->
                orquestrationGateway.updateStepRecord(id, stepId, orderId)
        );

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any(OrquestrationRecordORM.class));
    }
}
