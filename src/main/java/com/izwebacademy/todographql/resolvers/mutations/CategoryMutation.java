package com.izwebacademy.todographql.resolvers.mutations;

import com.izwebacademy.todographql.contracts.mutations.CategoryMutationContract;
import com.izwebacademy.todographql.inputs.CategoryInput;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.services.CategoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMutation implements GraphQLMutationResolver, CategoryMutationContract {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Category createCategory(CategoryInput input) {
        return categoryService.createCategory(input);
    }

    @Override
    public Category updateCategory(CategoryInput input) {
        return categoryService.updateCategory(input);
    }

    @Override
    public Category deleteCategory(Long id) {
        return categoryService.deleteCategory(id);
    }
}
