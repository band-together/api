package com.band.api.exceptions;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

/**
 * Removes location and stacktrace from exceptions returned to client
 */
public class GraphQLErrorAdapter implements GraphQLError {

    private GraphQLError error;

    public GraphQLErrorAdapter(GraphQLError error) {
        this.error = error;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return error.getExtensions();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return error.getErrorType();
    }

    @Override
    public List<Object> getPath() {
        return error.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return error.toSpecification();
    }

    @Override
    public String getMessage() {
        if (error instanceof ExceptionWhileDataFetching) {
            return ((ExceptionWhileDataFetching) error).getException().getMessage();
        }
        else {
            return error.getMessage();
        }
    }
}
