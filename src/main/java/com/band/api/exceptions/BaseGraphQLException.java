package com.band.api.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseGraphQLException extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();

    public BaseGraphQLException(String message) {
        super(message);
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
}
