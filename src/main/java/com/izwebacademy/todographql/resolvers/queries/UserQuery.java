package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.contracts.queries.UserQueryContract;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {

    @Autowired
    private UserQueryContract userService;


    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    public User getUser(Long id) {
        return userService.getUser(id);
    }
}
