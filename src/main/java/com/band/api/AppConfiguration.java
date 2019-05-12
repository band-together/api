package com.band.api;

import com.band.api.exceptions.BaseGraphQLException;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class AppConfiguration {

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return (errors)->processErrors(errors);
    }

    private List<GraphQLError> processErrors(List<GraphQLError> errors) {

        return Stream.concat(
                errors.stream()
                        .filter(this::isClientError),
                errors.stream()
                        .filter(Predicate.not(this::isClientError))
                        .map(BaseGraphQLException::new)
        ).collect(Collectors.toList());
    }

    private boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }


}
