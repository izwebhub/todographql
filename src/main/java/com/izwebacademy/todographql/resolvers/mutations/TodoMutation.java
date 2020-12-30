package com.izwebacademy.todographql.resolvers.mutations;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Todo;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
@PermissionFactory
@Validated
public class TodoMutation implements GraphQLMutationResolver {

	@Autowired
	private TodoMutationContract todoService;

	@PermissionMetaData(permissionName = "CREATE_TODO", description = "Create New Todo")
	public Todo createTodo(@Valid TodoInput input) {
		return todoService.createTodo(input);
	}

	@PermissionMetaData(permissionName = "UPDATE_TODO", description = "Update Todo")
	public Todo updateTodo(Long id, TodoInput input) {
		return todoService.updateTodo(id, input);
	}

	@PermissionMetaData(permissionName = "DELETE_TODO", description = "Delete Todo")
	public Todo deleteTodo(Long id) {
		return todoService.deleteTodo(id);
	}

	@PermissionMetaData(permissionName = "COMPLETE_MY_TODO", description = "Complete My Todo")
	public Todo completeMyTodo(Long todoId, String completeDate) {
		return todoService.completeMyTodo(todoId, completeDate);
	}

	@PermissionMetaData(permissionName = "COMPLETE_USER_TODO", description = "Complete User Todo")
	public Todo completeUserTodo(Long userId, Long todoId, String completeDate) {
		return todoService.completeUserTodo(userId, todoId, completeDate);
	}
}
