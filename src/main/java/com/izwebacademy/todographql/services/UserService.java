package com.izwebacademy.todographql.services;

import com.izwebacademy.todographql.EntityException;
import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.contracts.mutations.UserMutationContract;
import com.izwebacademy.todographql.contracts.queries.UserQueryContract;
import com.izwebacademy.todographql.inputs.AuthInput;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.inputs.UserInput;
import com.izwebacademy.todographql.inputs.UserPermissionInput;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.models.TokenResponse;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.PermissionRepository;
import com.izwebacademy.todographql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
public class UserService implements UserMutationContract, UserQueryContract {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(UserInput input) {
        String username = input.getUsername();
        Optional<User> dbUser = userRepository.findByUsernameAndActiveTrue(username);
        if (dbUser.isPresent()) {
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

    @Override
    public List<Permission> assignPermissions(UserPermissionInput input) {

        Optional<User> dbUser = userRepository.findByIdAndActiveTrue(input.getUserId());

        if(!dbUser.isPresent()) {
            throw new EntityException("User not found", "userId");
        }

        List<Long> permissionIds = input.getPermissionIds();

        List<Permission> permissions = new CopyOnWriteArrayList<>();

        for (Long permissionId :
                permissionIds) {
            Optional<Permission> dbPerm = permissionRepository.findByIdAndActiveTrue(permissionId);
            if(!dbPerm.isPresent()) {
                throw new EntityException("Permission not found", permissionId);
            }

            permissions.add(dbPerm.get());
        }

        User user = dbUser.get();
        if(!user.getPermissions().isEmpty()) {
            // Clean the exists
            int deleted = userRepository.deleteAllUserPerms(input.getUserId());
            if(deleted == 0) {
                throw new EntityException("User permissions could be cleared", null);
            }
        }

        user.setPermissions(permissions);
        userRepository.save(user);

        return permissions;
    }

    @Override
    public TokenResponse authUser(AuthInput input) {
        return null;
    }

    private String getPassword(UserInput input) {
        return bCryptPasswordEncoder.encode(input.getPassword());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByActiveTrue();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> dbUser = userRepository.findByIdAndActiveTrue(id);
        if(!dbUser.isPresent()) {
            throw new EntityException("User not found", id);
        }
        return dbUser.get();
    }
}
