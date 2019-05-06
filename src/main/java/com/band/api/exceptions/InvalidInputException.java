package com.band.api.exceptions;

public class InvalidInputException extends BaseGraphQLException {
    public InvalidInputException(String message) {
        super(message);
    }
}
