package com.izwebacademy.todographql.resolvers.queries;

import java.util.List;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izwebacademy.todographql.contracts.queries.CategoryQueryContract;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.services.CategoryService;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
@PermissionFactory
public class CategoryQuery implements GraphQLQueryResolver {

    @Autowired
    private CategoryQueryContract categoryService;

    @PermissionMetaData(permissionName = "GET_CATEGORIES", description = "Get All Categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PermissionMetaData(permissionName = "GET_CATEGORY", description = "Get Category")
    public Category getCategory(Long id) {
        return categoryService.getCategory(id);
    }

}
