package com.izwebacademy.todographql.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import com.izwebacademy.todographql.utils.EntityException;
import com.izwebacademy.todographql.contracts.mutations.CategoryMutationContract;
import com.izwebacademy.todographql.inputs.CategoryInput;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izwebacademy.todographql.contracts.queries.CategoryQueryContract;
import com.izwebacademy.todographql.contracts.subscriptions.CategorySubscriptionContract;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService implements CategoryQueryContract, CategoryMutationContract, CategorySubscriptionContract {

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
		throw new EntityException("Category not found", null);
	}

	@Override
	public Category createCategory(CategoryInput input) {
		Optional<Category> dbCategory = categoryRepository.findByNameAndActiveTrue(input.getName());
		if (dbCategory.isPresent()) {
			throw new EntityException("Category exists", "name");
		}

		Category category = new Category();
		category.setName(input.getName());
		category.setDescription(input.getDescription());

		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Long id, CategoryInput input) {

		if (id == null) {
			throw new EntityException("Please provide id", "id");
		}

		Optional<Category> dbCategory = categoryRepository.findByNameAndIdAndActiveTrue(input.getName(), id);
		if (dbCategory.isPresent()) {
			throw new EntityException("Category exists", "name");
		}

		Category category = categoryRepository.getOne(id);
		category.setName(input.getName());
		category.setDescription(input.getDescription());

		return categoryRepository.save(category);
	}

	@Override
	public Category deleteCategory(Long id) {
		if (id == null) {
			throw new EntityException("Please provide id", "id");
		}

		Optional<Category> dbCategory = categoryRepository.findByIdAndActiveTrue(id);

		if (dbCategory.isPresent()) {
			// delete it
			int deleted = categoryRepository.deleteCategory(id);
			if (deleted == 0)
				throw new EntityException("Category could not be deleted", null);

			return categoryRepository.getOne(id);
		}

		throw new EntityException("Category not found", null);
	}

	@Override
	public Publisher<List<Category>> categories() {
		return (Subscriber<? super List<Category>> s) -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			List<Category> categories = categoryRepository.findAllByActiveTrue();
			s.onNext(categories);
		}, 0, 1, TimeUnit.SECONDS);
	}
}
