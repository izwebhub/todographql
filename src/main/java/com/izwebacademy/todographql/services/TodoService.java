package com.izwebacademy.todographql.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.enums.TodoStatus;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.CategoryRepository;
import com.izwebacademy.todographql.repositories.TodoRepository;
import com.izwebacademy.todographql.repositories.UserRepository;
import com.izwebacademy.todographql.utils.EntityException;
import com.izwebacademy.todographql.utils.GenericException;
import com.izwebacademy.todographql.utils.JwtUtil;

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

		if (startDate.isAfter(endDate)) {
			throw new GenericException("End Date should be after Start date", null);
		}

		Todo todo = new Todo(input.getTitle(), input.getDescription(), startDate, endDate, dbCategory.get(),
				dbUser.get());

		return todoRepository.save(todo);
	}

	private LocalDate convertStringToLD(String dateInput) {
		try {
			return LocalDate.parse(dateInput);
		} catch (Exception e) {
			throw new GenericException("Check your date format", e.getLocalizedMessage());
		}

	}

	@Override
	public Todo updateTodo(Long id, TodoInput input) {
		Todo dbTodo = todoRepository.findByIdAndActiveTrue(id);
		if (dbTodo == null) {
			throw new EntityException("Todo not found", id);
		}

		LocalDate startDate = this.convertStringToLD(input.getStartDate());
		LocalDate endDate = this.convertStringToLD(input.getEndDate());

		if (startDate.isAfter(endDate)) {
			throw new GenericException("End Date should be after Start date", null);
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

		String title = input.getTitle();
		Optional<Todo> existTodo = todoRepository.checkExistence(title, dbUser.get(), id);

		if (existTodo.isPresent()) {
			throw new EntityException("Todo exists", title);
		}

		Long categoryId = input.getCategoryId();
		Optional<Category> dbCategory = categoryRepository.findByIdAndActiveTrue(categoryId);
		if (!dbCategory.isPresent()) {
			throw new EntityException("Category not found!", "categoryId");
		}

		// Check if the todo is not completed yet!
		if (dbTodo.getCompleted()) {
			throw new GenericException("Todo is completed", dbTodo);
		}

		dbTodo.setTitle(title);
		dbTodo.setDescription(input.getDescription());
		dbTodo.setCategory(dbCategory.get());
		dbTodo.setStartDate(startDate);
		dbTodo.setEndDate(endDate);

		return todoRepository.save(dbTodo);
	}

	@Override
	public Todo deleteTodo(Long id) {

		Todo dbTodo = todoRepository.findByIdAndActiveTrue(id);
		if (dbTodo == null) {
			throw new EntityException("Todo not found", id);
		}

		try {
			todoRepository.deleteTodo(id);
		} catch (Exception e) {
			throw new GenericException("Todo could not be deleted", id);
		}

		return dbTodo;
	}

	@Override
	public List<Todo> getAllOverDueTodos(LocalDate endDate) {
		return null;
	}

	@Override
	public List<Todo> getMyOverDueTodos(LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Todo completeMyTodo(Long todoId, String completeDate) {
		Todo dbTodo = todoRepository.findByIdAndActiveTrue(todoId);
		if (dbTodo == null) {
			throw new EntityException("Todo not found", todoId);
		}
		
		Long userId = jwtUtil.getUserId();
		
		User user = userRepository.getOne(userId);
		
		if (!dbTodo.getCreatedBy().equals(user)) {
			throw new GenericException("You can not complete someone's todo", dbTodo);
		}

		LocalDate completedDate = this.convertStringToLD(completeDate);

		if (dbTodo.getCompletedDate().equals(completedDate)) {
			// Complete on time
			dbTodo.setCompleted(true);
			dbTodo.setStatus(TodoStatus.COMPLETED);
			dbTodo.setCompletedDate(completedDate);
			return todoRepository.save(dbTodo);
		} else if (dbTodo.getCompletedDate().isBefore(completedDate)) {
			// Overdue
			dbTodo.setStatus(TodoStatus.OVERDUED);
			dbTodo.setCompletedDate(completedDate);
			return todoRepository.save(dbTodo);
		}

		return dbTodo;
	}

	@Override
	public Todo completeUserTodo(Long userId, Long todoId, String completeDate) {
		Todo dbTodo = todoRepository.findByIdAndActiveTrue(todoId);
		if (dbTodo == null) {
			throw new EntityException("Todo not found", todoId);
		}

		User user = userRepository.getOne(userId);

		if (user == null) {
			throw new EntityException("User not found", userId);
		}

		if (!dbTodo.getCreatedBy().equals(user)) {
			throw new GenericException("You can not complete someone's todo", dbTodo);
		}

		LocalDate completedDate = this.convertStringToLD(completeDate);

		if (dbTodo.getCompletedDate().equals(completedDate)) {
			// Complete on time
			dbTodo.setCompleted(true);
			dbTodo.setStatus(TodoStatus.COMPLETED);
			dbTodo.setCompletedDate(completedDate);
			return todoRepository.save(dbTodo);
		} else if (dbTodo.getCompletedDate().isBefore(completedDate)) {
			// Overdue
			dbTodo.setStatus(TodoStatus.OVERDUED);
			dbTodo.setCompletedDate(completedDate);
			return todoRepository.save(dbTodo);
		}

		return dbTodo;
	}
}
