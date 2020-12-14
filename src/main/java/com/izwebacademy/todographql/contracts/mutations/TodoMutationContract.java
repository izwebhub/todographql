package com.izwebacademy.todographql.contracts.mutations;

import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Todo;

public interface TodoMutationContract {
    Todo createTodo(TodoInput input);
}
