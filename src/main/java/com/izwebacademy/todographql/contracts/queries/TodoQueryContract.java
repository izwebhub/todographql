package com.izwebacademy.todographql.contracts.queries;

import com.izwebacademy.todographql.models.Todo;

import java.util.List;

public interface TodoQueryContract {
    List<Todo> getAllTodos();
    List<Todo> getUserTodos(Long userId);
    Todo getTodo(Long id);
}
