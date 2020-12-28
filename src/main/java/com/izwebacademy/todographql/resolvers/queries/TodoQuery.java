package com.izwebacademy.todographql.resolvers.queries;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.models.Todo;

import graphql.kickstart.tools.GraphQLQueryResolver;

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

	@PermissionMetaData(permissionName = "GET_MY_TODOS", description = "Get My Todos")
	public List<Todo> getMyTodos() {
		return todoService.getMyTodos();
	}

	@PermissionMetaData(permissionName = "GET_ALL_OVER_DUE_TODOS", description = "Get All Over Due Todos")
	public List<Todo> getAllOverDueTodos(LocalDate endDate) {
		return todoService.getAllOverDueTodos(endDate);
	}

	@PermissionMetaData(permissionName = "GET_MY_OVER_DUE_TODOS", description = "Get My Over Due Todos")
	public List<Todo> getMyOverDueTodos(LocalDate endDate) {
		return todoService.getMyOverDueTodos(endDate);
	}
}
