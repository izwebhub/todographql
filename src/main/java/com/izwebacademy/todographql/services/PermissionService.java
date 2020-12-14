package com.izwebacademy.todographql.services;

import com.izwebacademy.todographql.EntityException;
import com.izwebacademy.todographql.contracts.queries.PermissionQueryContract;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.PermissionRepository;
import com.izwebacademy.todographql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PermissionService implements PermissionQueryContract {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<Permission> getPermission(Long id) {
        return permissionRepository.findByIdAndActiveTrue(id);
    }

    @Override
    public List<Permission> getUserPermission(Long userId) {

        Optional<User> dbUser = userRepository.findByIdAndActiveTrue(userId);
        if(!dbUser.isPresent()) {
            throw new EntityException("User not found", userId);
        }

        return dbUser.get().getPermissions();
    }
}
