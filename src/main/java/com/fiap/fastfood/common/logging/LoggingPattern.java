package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[COMMAND - %s] Sending Command for %s... | Message: %s";
    public static final String COMMAND_END_LOG = "[COMMAND - %s] Command Succesfully sent for %s.";
    public static final String COMMAND_ERROR_LOG = "[COMMAND - %s] Error sending command for %s. | Error Message: %s | Message: %s";
    public static final String RESPONSE_INIT_LOG = "[RESPONSE] Response received from %s. ";
    public static final String RESPONSE_END_LOG = "[RESPONSE] Response successfully received from %s.";
    public static final String RESPONSE_ERROR_LOG = "[RESPONSE] Error receiving response from %s. | Error Message: %s | Message: %s";
}
