package com.izwebacademy.todographql.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.izwebacademy.todographql.models.Category;

@Repository // But not necessary
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByActiveTrue();

    Optional<Category> findByIdAndActiveTrue(Long id);

    Optional<Category> findByNameAndActiveTrue(String name);

    Optional<Category> findByNameAndIdAndActiveTrue(String name, Long id);

    @Modifying
    @Query("UPDATE Category c SET c.active=false WHERE c.id=?1")
    int deleteCategory(Long id);
}
