package com.izwebacademy.todographql.inputs;

import com.izwebacademy.todographql.models.Permission;

import java.util.List;

public class UserPermissionInput {
    private Long userId;
    private List<Long> permissionIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
