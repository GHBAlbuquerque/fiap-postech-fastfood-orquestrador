package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[COMMAND] SagaId: {} | Sending command for {}... | Message: {}";
    public static final String COMMAND_END_LOG = "[COMMAND] SagaId: {} | Command succesfully sent to {}.";
    public static final String COMMAND_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending command to {}. | Error Message: {} | Message: {}";

    public static final String RESPONSE_INIT_LOG = "[RESPONSE] SagaId: {} | Response received from {}. ";
    public static final String RESPONSE_END_LOG = "[RESPONSE] SagaId: {} | Response successfully received from {}.";
    public static final String RESPONSE_ERROR_LOG = "[RESPONSE] SagaId: {} | Error receiving response from {}. | Error Message: {} | Message: {}";

    public static final String ORQUESTRATION_STEP_INFORMER = "[ORQUESTRATION] SagaId: {} | Message received with executed step: {}";
    public static final String ORQUESTRATION_INIT_LOG = "[ORQUESTRATION] Initating orquestration for {} | Request: {}";
    public static final String ORQUESTRATION_STEP_LOG = "[ORQUESTRATION] SagaId: {} | Initating orquestration for {}";
    public static final String ORQUESTRATION_END_LOG = "[ORQUESTRATION] SagaId: {} | Ended orquestration for {}";
    public static final String ORQUESTRATION_ERROR_LOG = "[ORQUESTRATION] SagaId: {} | Error orquestrating {} | Error Message: {} | Message: {}";

    public static final String ORQUESTRATION_NO_NEXT_TRANSACTION = "[ORQUESTRATION] SagaId: {} | Step {} does not have a Next Transaction.";
    public static final String ORQUESTRATION_NO_COMPENSATING_TRANSACTION = "[ORQUESTRATION] SagaId: {} | Step {} does not have Compensating Transactions.";
}
