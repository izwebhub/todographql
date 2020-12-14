package com.izwebacademy.todographql.contracts.mutations;

import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.models.User;

public interface UserMutationContract {
    User createUser(UserInput input);
}
