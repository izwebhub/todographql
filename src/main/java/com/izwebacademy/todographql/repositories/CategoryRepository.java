package com.izwebacademy.todographql.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izwebacademy.todographql.models.Category;

@Repository // But not necessary
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByActiveTrue();

    Optional<Category> findByIdAndActiveTrue(Long id);
}
