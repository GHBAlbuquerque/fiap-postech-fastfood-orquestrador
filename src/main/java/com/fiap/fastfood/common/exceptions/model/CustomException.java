package com.fiap.fastfood.common.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {

    private final String code;
    private List<CustomError> errors;

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String code, String message, List<CustomError> customErrors) {
        super(message);
        this.code = code;
        this.errors = customErrors;
    }
}
