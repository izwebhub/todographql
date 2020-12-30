package com.izwebacademy.todographql.repositories;

import com.izwebacademy.todographql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

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

	@Query("SELECT t FROM Todo t WHERE t.active=true AND t.title=?1 AND t.createdBy=?2 AND t.id!=?3")
	Optional<Todo> checkExistence(String title, User user, Long id);

	@Modifying
	@Query("UPDATE Todo t SET t.active=false WHERE t.id=?1")
	int deleteTodo(Long id);


}
