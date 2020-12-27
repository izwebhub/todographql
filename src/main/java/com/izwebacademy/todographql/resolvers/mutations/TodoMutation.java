package com.izwebacademy.todographql.resolvers.mutations;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.services.TodoService;
import graphql.kickstart.tools.GraphQLMutationResolver;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
}
