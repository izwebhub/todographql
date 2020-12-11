package com.izwebacademy.todographql.handlers;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoGraphlHandler implements GraphQLErrorHandler {
    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors.stream().map(this::getNested).collect(Collectors.toList());
    }

    private GraphQLError getNested(GraphQLError graphQLError) {
        if(graphQLError instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exErr = (ExceptionWhileDataFetching) graphQLError;
            if(exErr.getException() instanceof GraphQLError) {
                return (GraphQLError) exErr.getException();
            }
        }

        return graphQLError;
    }
}
