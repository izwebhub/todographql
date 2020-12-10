package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.izwebacademy.todographql.models.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
