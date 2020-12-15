package com.izwebacademy.todographql.utils;

import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class Authenticator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // We will use our own custom security
    public boolean attempt(String username, String password) {

        Optional<User> dbUser = userRepository.findByUsernameAndActiveTrue(username);
        if (dbUser.isPresent()) {
            User user = dbUser.get();
            String userPassword = user.getPassword();
            return bCryptPasswordEncoder.matches(password, userPassword);
        }
        return false;
    }
}
