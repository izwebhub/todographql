package com.izwebacademy.todographql.resolvers.queries;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izwebacademy.todographql.contracts.queries.CategoryQueryContract;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.services.CategoryService;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class CategoryQuery implements GraphQLQueryResolver {

    @Autowired
    private CategoryQueryContract categoryService;


    public List<Category> getCategories() {
        return categoryService.getCategories();
    }


    public Category getCategory(Long id) {
        return categoryService.getCategory(id);
    }

}
