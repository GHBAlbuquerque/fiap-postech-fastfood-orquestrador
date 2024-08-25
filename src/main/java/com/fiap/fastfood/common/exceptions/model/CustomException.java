package com.fiap.fastfood.common.exceptions.model;

import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {

    private final ExceptionCodes code;
    private List<CustomError> errors;

    public CustomException(ExceptionCodes code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(ExceptionCodes code, String message, List<CustomError> customErrors) {
        super(message);
        this.code = code;
        this.errors = customErrors;
    }
}
