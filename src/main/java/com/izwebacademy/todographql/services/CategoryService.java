package com.izwebacademy.todographql.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.izwebacademy.todographql.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izwebacademy.todographql.contracts.queries.CategoryQueryContract;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService implements CategoryQueryContract {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAllByActiveTrue();
    }

    @Override
    public Category getCategory(Long id) {
        Optional<Category> dbCategory = categoryRepository.findByIdAndActiveTrue(id);
        if (dbCategory.isPresent()) {
            return dbCategory.get();
        }
        throw new EntityNotFoundException("Category not found", null);
    }

}
