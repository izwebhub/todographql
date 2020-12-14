package com.izwebacademy.todographql.contracts.queries;

import com.izwebacademy.todographql.models.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionQueryContract {
    List<Permission> getAllPermissions();
    Optional<Permission> getPermission(Long id);
    List<Permission> getUserPermission(Long userId);
}
