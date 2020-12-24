package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.services.TodoService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PermissionFactory
public class TodoQuery implements GraphQLQueryResolver {

    @Autowired
    private TodoQueryContract todoService;

    @PermissionMetaData(permissionName = "GET_TODOS", description = "Get All Todos")
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PermissionMetaData(permissionName = "GET_USER_TODO", description = "Get User Todo")
    public List<Todo> getUserTodos(Long userId) {
        return todoService.getUserTodos(userId);
    }

    @PermissionMetaData(permissionName = "GET_TODO", description = "Get Todo")
    public Todo getTodo(Long id) {
        return todoService.getTodo(id);
    }
}
