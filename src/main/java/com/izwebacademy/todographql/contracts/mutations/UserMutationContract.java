package com.izwebacademy.todographql.contracts.mutations;

import com.izwebacademy.todographql.inputs.AuthInput;
import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.inputs.UserPermissionInput;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.TokenResponse;
import com.izwebacademy.todographql.models.User;

import java.util.List;

public interface UserMutationContract {
	User createUser(UserInput input);

	List<Permission> assignPermissions(UserPermissionInput input);

	TokenResponse authUser(AuthInput input);

	User activateUser(Long userId);

	User blockUser(Long userId);
}
