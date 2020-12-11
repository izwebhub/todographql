package com.izwebacademy.todographql.contracts.queries;

import java.util.List;

import com.izwebacademy.todographql.models.Category;

public interface CategoryQueryContract {
	List<Category> getCategories();
	
	Category getCategory(Long id);
}
