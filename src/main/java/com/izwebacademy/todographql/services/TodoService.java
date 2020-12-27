package com.izwebacademy.todographql.services;

import com.izwebacademy.todographql.models.JwtUser;
import com.izwebacademy.todographql.utils.EntityException;
import com.izwebacademy.todographql.utils.GenericException;
import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.CategoryRepository;
import com.izwebacademy.todographql.repositories.TodoRepository;
import com.izwebacademy.todographql.repositories.UserRepository;
import com.izwebacademy.todographql.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService implements TodoQueryContract, TodoMutationContract {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public List<Todo> getAllTodos() {
		return todoRepository.findAllByActiveTrue();
	}

	@Override
	public List<Todo> getUserTodos(Long userId) {
		Optional<User> dbUser = userRepository.findByIdAndActiveTrue(userId);
		if (!dbUser.isPresent()) {
			throw new EntityException("User not found", userId);
		}
		return todoRepository.findByCreatedByAndActiveTrue(dbUser.get());
	}

	@Override
	public Todo getTodo(Long id) {
		return todoRepository.findByIdAndActiveTrue(id);
	}

	@Override
	public List<Todo> getMyTodos() {
		Long userId = jwtUtil.getUserId();
		User dbUser = userRepository.getOne(userId);
		if (dbUser == null) {
			throw new EntityException("User not found", userId);
		}

		return todoRepository.findByCreatedByAndActiveTrue(dbUser);
	}

	@Override
	public Todo createTodo(TodoInput input) {

		Long categoryId = input.getCategoryId();
		Optional<Category> dbCategory = categoryRepository.findByIdAndActiveTrue(categoryId);
		if (!dbCategory.isPresent()) {
			throw new EntityException("Category not found!", "categoryId");
		}

		Long userId = input.getUserId();

		if (userId == null) {
			// Current Loggin User
			userId = jwtUtil.getUserId();
		}

		Optional<User> dbUser = userRepository.findByIdAndActiveTrue(userId);
		if (!dbUser.isPresent()) {
			throw new EntityException("User not found", "userId");
		}

		Optional<Todo> dbTodo = todoRepository.checkExistence(input.getTitle(), dbUser.get());
		if (dbTodo.isPresent()) {
			throw new EntityException("Todo exists", null);
		}

		LocalDate startDate = this.convertStringToLD(input.getStartDate());
		LocalDate endDate = this.convertStringToLD(input.getEndDate());
		
		if(startDate.isAfter(endDate)) {
			throw new GenericException("End Date should be after Start date", null);
		}
		
		Todo todo = new Todo(input.getTitle(), input.getDescription(), startDate,
				endDate, dbCategory.get(), dbUser.get());

		return todoRepository.save(todo);
	}

	private LocalDate convertStringToLD(String dateInput) {
		return LocalDate.parse(dateInput);
	}
}
