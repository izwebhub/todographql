package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
