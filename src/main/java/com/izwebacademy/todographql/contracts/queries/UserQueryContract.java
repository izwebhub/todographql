package com.izwebacademy.todographql.contracts.queries;

import com.izwebacademy.todographql.models.User;

import java.util.List;

public interface UserQueryContract {
    List<User> getAllUsers();
    User getUser(Long id);

}
