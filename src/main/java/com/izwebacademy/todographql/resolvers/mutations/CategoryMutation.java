package com.izwebacademy.todographql.resolvers.mutations;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.mutations.CategoryMutationContract;
import com.izwebacademy.todographql.inputs.CategoryInput;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.services.CategoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Validated
@PermissionFactory
public class CategoryMutation implements GraphQLMutationResolver {
    @Autowired
    private CategoryMutationContract categoryService;

    @PermissionMetaData(permissionName = "CREATE_CATEGORY", description = "Create New Category")
    public Category createCategory(@Valid CategoryInput input) {
        return categoryService.createCategory(input);
    }

    @PermissionMetaData(permissionName = "UPDATE_CATEGORY", description = "Update existing Category")
    public Category updateCategory(Long id, @Valid CategoryInput input) {
        return categoryService.updateCategory(id, input);
    }

    @PermissionMetaData(permissionName = "DELETE_CATEGORY", description = "Delete existing Category")
    public Category deleteCategory(Long id) {
        return categoryService.deleteCategory(id);
    }
}
