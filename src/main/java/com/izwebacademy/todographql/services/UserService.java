package com.izwebacademy.todographql.services;

import com.izwebacademy.todographql.EntityException;
import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.contracts.mutations.UserMutationContract;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserMutationContract {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(UserInput input) {
        String username = input.getUsernmae();
        Optional<User> dbUser = userRepository.findByUsernameAndActiveTrue(username);
        if(dbUser.isPresent()) {
            throw new EntityException("User exists", "username");
        }

        // Validation is done on Entity Base Annotation
        User user = new User();
        user.setUsername(username);
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(getPassword(input));

        return userRepository.save(user);
    }

    private String getPassword(UserInput input) {
        return bCryptPasswordEncoder.encode(input.getPassword());
    }
}
