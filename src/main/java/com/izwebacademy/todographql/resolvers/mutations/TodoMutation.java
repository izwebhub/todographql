package com.izwebacademy.todographql.resolvers.mutations;

import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.services.TodoService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoMutation implements GraphQLMutationResolver {

    @Autowired
    private TodoMutationContract todoService;

    public Todo createTodo(TodoInput input) {
        return todoService.createTodo(input);
    }
}
