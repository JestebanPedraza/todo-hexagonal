package com.app.todo_hexagonal.application.port.out;

import com.app.todo_hexagonal.domain.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Task save(Task task);
    List<Task> listAll();
    Optional<Task> findById(Long id);
    Task update(Task task);
}
