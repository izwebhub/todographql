package com.izwebacademy.todographql.resolvers.mutations;

import com.izwebacademy.todographql.contracts.mutations.UserMutationContract;
import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.inputs.UserPermissionInput;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.services.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMutation implements GraphQLMutationResolver, UserMutationContract {

    @Autowired
    private UserService userService;


    @Override
    public User createUser(UserInput input) {
        return userService.createUser(input);
    }

    @Override
    public List<Permission> assignPermissions(UserPermissionInput input) {
        return userService.assignPermissions(input);
    }
}
