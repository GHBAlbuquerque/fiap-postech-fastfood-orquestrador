package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[COMMAND] OrderId: %s | Sending Command for %s... | Message: %s";
    public static final String COMMAND_END_LOG = "[COMMAND] OrderId: %s | Command Succesfully sent for %s.";
    public static final String COMMAND_ERROR_LOG = "[COMMAND] OrderId: %s | Error sending command for %s. | Error Message: %s | Message: %s";
    public static final String RESPONSE_INIT_LOG = "[RESPONSE] OrderId: %s | Response received from %s. ";
    public static final String RESPONSE_END_LOG = "[RESPONSE] OrderId: %s | Response successfully received from %s.";
    public static final String RESPONSE_ERROR_LOG = "[RESPONSE] OrderId: %s | Error receiving response from %s. | Error Message: %s | Message: %s";
    public static final String ORQUESTRATION_INIT_LOG = "[ORQUESTRATION] OrderId: %s | Initating orquestration for %s";
    public static final String ORQUESTRATION_END_LOG = "[ORQUESTRATION] OrderId: %s | Ended orquestration for %s";
    public static final String ORQUESTRATION_ERROR_LOG = "[ORQUESTRATION] OrderId: %s | Error orquestrating %s";
}
