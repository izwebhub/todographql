package com.izwebacademy.todographql.resolvers.queries;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.queries.PermissionQueryContract;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.services.PermissionService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@PermissionFactory
public class PermissionQuery implements GraphQLQueryResolver {

    @Autowired
    private PermissionQueryContract permissionService;

    @PermissionMetaData(permissionName = "GET_PERMISSIONS", description = "Get All Permissions")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @PermissionMetaData(permissionName = "GET_PERMISSION", description = "Get Permission")
    public Optional<Permission> getPermission(Long id) {
        return permissionService.getPermission(id);
    }

    @PermissionMetaData(permissionName = "GET_USER_PERMISSIONS", description = "Get User Permissions")
    public List<Permission> getUserPermission(Long userId) {
        return permissionService.getUserPermission(userId);
    }
}
