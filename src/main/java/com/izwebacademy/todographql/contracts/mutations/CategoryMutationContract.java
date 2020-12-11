package com.izwebacademy.todographql.contracts.mutations;

import com.izwebacademy.todographql.inputs.CategoryInput;
import com.izwebacademy.todographql.models.Category;

public interface CategoryMutationContract {
    Category createCategory(CategoryInput input);
    Category updateCategory(CategoryInput input);
    Category deleteCategory(Long id);
}
