package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.services.TodoService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoQuery implements GraphQLQueryResolver, TodoQueryContract {

    @Autowired
    private TodoService todoService;

    @Override
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @Override
    public List<Todo> getUserTodos(Long userId) {
        return todoService.getUserTodos(userId);
    }

    @Override
    public Todo getTodo(Long id) {
        return todoService.getTodo(id);
    }
}
