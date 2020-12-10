package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
