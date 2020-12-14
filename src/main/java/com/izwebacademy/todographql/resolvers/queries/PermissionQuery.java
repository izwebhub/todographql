package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.contracts.queries.PermissionQueryContract;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.services.PermissionService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PermissionQuery implements GraphQLQueryResolver, PermissionQueryContract {

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @Override
    public Optional<Permission> getPermission(Long id) {
        return permissionService.getPermission(id);
    }

    @Override
    public List<Permission> getUserPermission(Long userId) {
        return permissionService.getUserPermission(userId);
    }
}
