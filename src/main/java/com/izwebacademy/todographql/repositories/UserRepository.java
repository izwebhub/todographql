package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    String DELETE_USER_PERMS = "DELETE FROM `graphql_user_permissions` WHERE users_id=?1";

    Optional<User> findByIdAndActiveTrue(Long userId);

    Optional<User> findByUsernameAndActiveTrue(String usernmae);

    @Modifying
    @Query(value = DELETE_USER_PERMS, nativeQuery = true)
    int deleteAllUserPerms(Long userId);
}
