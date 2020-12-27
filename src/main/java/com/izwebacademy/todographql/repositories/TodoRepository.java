package com.izwebacademy.todographql.repositories;

import com.izwebacademy.todographql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.Todo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByActiveTrue();

    @Query("SELECT t FROM Todo t WHERE t.active=true AND t.title=?1 AND t.createdBy=?2")
    Optional<Todo> checkExistence(String title, User createsBy);

    List<Todo> findByCreatedByAndActiveTrue(User createBy);

    Todo findByIdAndActiveTrue(Long id);
}
