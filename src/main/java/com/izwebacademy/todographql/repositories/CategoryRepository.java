package com.izwebacademy.todographql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.izwebacademy.todographql.models.Category;

@Repository // But not necessary
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
