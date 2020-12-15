package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.services.TodoService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoQuery implements GraphQLQueryResolver {

    @Autowired
    private TodoQueryContract todoService;


    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }


    public List<Todo> getUserTodos(Long userId) {
        return todoService.getUserTodos(userId);
    }


    public Todo getTodo(Long id) {
        return todoService.getTodo(id);
    }
}
