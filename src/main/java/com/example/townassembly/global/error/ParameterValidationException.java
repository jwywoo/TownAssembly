package com.example.townassembly.global.error;

public class ParameterValidationException extends RuntimeException{

    public ParameterValidationException(String message) {
        super(message);
    }
}
