package com.izwebacademy.todographql.resolvers.mutations;

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
public class CategoryMutation implements GraphQLMutationResolver {
    @Autowired
    private CategoryMutationContract categoryService;


    public Category createCategory(@Valid CategoryInput input) {
        return categoryService.createCategory(input);
    }


    public Category updateCategory(CategoryInput input) {
        return categoryService.updateCategory(input);
    }

    public Category deleteCategory(Long id) {
        return categoryService.deleteCategory(id);
    }
}
