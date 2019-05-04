package com.band.api.exceptions;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseGraphQLException extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();

    private GraphQLError error = null;

    public BaseGraphQLException(String message) {
        super(message);
    }

    public BaseGraphQLException(GraphQLError error) {
        super(error.getMessage());
        this.error = error;
        extensions = error.getExtensions();
    }

    public void addExtension(String key, Object val) {
        extensions.put(key, val);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return null;
    }

    @Override
    public String getMessage() {
        if (error != null) {
            if (error instanceof ExceptionWhileDataFetching) {
                return ((ExceptionWhileDataFetching) error).getException().getMessage();
            }
            else {
                return error.getMessage();
            }
        }
        else {
            return super.getMessage();
        }
    }
}
