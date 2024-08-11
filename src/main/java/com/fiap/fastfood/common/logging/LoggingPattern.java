package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[COMMAND] SagaId: {} | Sending Command for {}... | Message: {}";
    public static final String COMMAND_END_LOG = "[COMMAND] SagaId: {} | Command Succesfully sent for {}.";
    public static final String COMMAND_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending command for {}. | Error Message: {} | Message: {}";
    public static final String RESPONSE_INIT_LOG = "[RESPONSE] SagaId: {} | Response received from {}. ";
    public static final String RESPONSE_END_LOG = "[RESPONSE] SagaId: {} | Response successfully received from {}.";
    public static final String RESPONSE_ERROR_LOG = "[RESPONSE] SagaId: {} | Error receiving response from {}. | Error Message: {} | Message: {}";
    public static final String ORQUESTRATION_INIT_LOG = "[ORQUESTRATION] Initating orquestration for {} | Request: {}";
    public static final String ORQUESTRATION_STEP_LOG = "[ORQUESTRATION] SagaId: {} | Initating orquestration for {}";
    public static final String ORQUESTRATION_END_LOG = "[ORQUESTRATION] SagaId: {} | Ended orquestration for {}";
    public static final String ORQUESTRATION_ERROR_LOG = "[ORQUESTRATION] SagaId: {} | Error orquestrating {}";
}
