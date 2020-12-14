package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndActiveTrue(Long userId);

    Optional<User> findByUsernameAndActiveTrue(String usernmae);
}
