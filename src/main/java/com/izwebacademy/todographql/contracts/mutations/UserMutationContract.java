package com.izwebacademy.todographql.contracts.mutations;

import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.inputs.UserPermissionInput;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.User;

import java.util.List;

public interface UserMutationContract {
    User createUser(UserInput input);
    List<Permission> assignPermissions(UserPermissionInput input);
}
