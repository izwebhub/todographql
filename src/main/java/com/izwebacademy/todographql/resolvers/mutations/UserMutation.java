package com.izwebacademy.todographql.resolvers.mutations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.mutations.UserMutationContract;
import com.izwebacademy.todographql.inputs.AuthInput;
import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.inputs.UserPermissionInput;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.TokenResponse;
import com.izwebacademy.todographql.models.User;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
@PermissionFactory
public class UserMutation implements GraphQLMutationResolver {

	@Autowired
	private UserMutationContract userService;

	public User createUser(UserInput input) {
		return userService.createUser(input);
	}

	@PermissionMetaData(permissionName = "ASSIGN_USER_PERMISSIONS", description = "Assign Permissions to user")
	public List<Permission> assignPermissions(UserPermissionInput input) {
		return userService.assignPermissions(input);
	}

	public TokenResponse authUser(AuthInput input) {
		return userService.authUser(input);
	}

	@PermissionMetaData(permissionName = "ACTIVATE_USER", description = "Activate User")
	public User activateUser(Long userId) {
		return userService.activateUser(userId);
	}

	@PermissionMetaData(permissionName = "BLOCK_USER", description = "Block User")
	public User blockUser(Long userId) {
		return userService.blockUser(userId);
	}
}
