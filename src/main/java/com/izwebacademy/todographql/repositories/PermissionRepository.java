package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByIdAndActiveTrue(Long permissionId);

    List<Permission> findAllByActiveTrue();
}
