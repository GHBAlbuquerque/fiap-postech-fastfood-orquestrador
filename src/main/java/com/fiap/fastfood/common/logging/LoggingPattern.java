package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[COMMAND] OrderId: {} | Sending Command for {}... | Message: {}";
    public static final String COMMAND_END_LOG = "[COMMAND] OrderId: {} | Command Succesfully sent for {}.";
    public static final String COMMAND_ERROR_LOG = "[COMMAND] OrderId: {} | Error sending command for {}. | Error Message: {} | Message: {}";
    public static final String RESPONSE_INIT_LOG = "[RESPONSE] OrderId: {} | Response received from {}. ";
    public static final String RESPONSE_END_LOG = "[RESPONSE] OrderId: {} | Response successfully received from {}.";
    public static final String RESPONSE_ERROR_LOG = "[RESPONSE] OrderId: {} | Error receiving response from {}. | Error Message: {} | Message: {}";
    public static final String ORQUESTRATION_INIT_LOG = "[ORQUESTRATION] OrderId: {} | Initating orquestration for {}";
    public static final String ORQUESTRATION_END_LOG = "[ORQUESTRATION] OrderId: {} | Ended orquestration for {}";
    public static final String ORQUESTRATION_ERROR_LOG = "[ORQUESTRATION] OrderId: {} | Error orquestrating {}";
}
