package com.thoughtworks.ts.exceptions;

public class InvalidRequestException extends Exception {

    private final String message;

    public InvalidRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
