package com.izwebacademy.todographql.services;

import com.izwebacademy.todographql.utils.EntityException;
import com.izwebacademy.todographql.contracts.mutations.TodoMutationContract;
import com.izwebacademy.todographql.contracts.queries.TodoQueryContract;
import com.izwebacademy.todographql.inputs.TodoInput;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.models.Todo;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.CategoryRepository;
import com.izwebacademy.todographql.repositories.TodoRepository;
import com.izwebacademy.todographql.repositories.UserRepository;
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

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAllByActiveTrue();
    }

    @Override
    public List<Todo> getUserTodos(Long userId) {
        return todoRepository.findByCreatedByAndActiveTrue(userId);
    }

    @Override
    public Todo getTodo(Long id) {
        return todoRepository.findByIdAndActiveTrue(id);
    }

    @Override
    public Todo createTodo(TodoInput input) {

        Long categoryId = input.getCategoryId();
        Optional<Category> dbCategory = categoryRepository.findByIdAndActiveTrue(categoryId);
        if (!dbCategory.isPresent()) {
            throw new EntityException("Category not found!", "categoryId");
        }
        Optional<User> dbUser = userRepository.findByIdAndActiveTrue(input.getUserId());
        if (!dbUser.isPresent()) {
            throw new EntityException("User not found", "userId");
        }

        Optional<Todo> dbTodo = todoRepository.checkExistence(input.getTitle(), dbUser.get());
        if (dbTodo.isPresent()) {
            throw new EntityException("Todo exists", null);
        }

        Todo todo = new Todo(input.getTitle(), input.getDescription(), this.convertStringToLD(input.getStartDate()), this.convertStringToLD(input.getEndDate()), dbCategory.get(), dbUser.get());

        return todoRepository.save(todo);
    }

    private LocalDate convertStringToLD(String dateInput) {
        return LocalDate.parse(dateInput);
    }
}
