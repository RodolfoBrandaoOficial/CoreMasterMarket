package com.rodolfobrandao.coremastermarket.tools;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final String message;
    private final int status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
