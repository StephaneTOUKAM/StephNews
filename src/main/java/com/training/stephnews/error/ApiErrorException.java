package com.training.stephnews.error;

public class ApiErrorException extends RuntimeException {
    public ApiErrorException(String message) {
        super(message);
    }
}
