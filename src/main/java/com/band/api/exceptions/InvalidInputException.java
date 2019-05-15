package com.band.api.exceptions;

import graphql.GraphQLError;

public class InvalidInputException extends BaseGraphQLException implements GraphQLError {
    public InvalidInputException(String message) {
        super(message);
    }
}
