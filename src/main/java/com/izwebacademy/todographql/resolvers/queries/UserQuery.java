package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.queries.UserQueryContract;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PermissionFactory
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserQueryContract userService;

    @PermissionMetaData(permissionName = "GET_USERS", description = "Get Users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PermissionMetaData(permissionName = "GET_USER", description = "Get User")
    public User getUser(Long id) {
        return userService.getUser(id);
    }
}
