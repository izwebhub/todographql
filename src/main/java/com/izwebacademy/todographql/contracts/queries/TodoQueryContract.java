package com.izwebacademy.todographql.contracts.queries;

import java.time.LocalDate;
import java.util.List;

import com.izwebacademy.todographql.models.Todo;

public interface TodoQueryContract {
	List<Todo> getAllTodos();

	List<Todo> getUserTodos(Long userId);

	Todo getTodo(Long id);

	List<Todo> getMyTodos();

	List<Todo> getAllOverDueTodos(LocalDate endDate);

	List<Todo> getMyOverDueTodos(LocalDate endDate);
}
