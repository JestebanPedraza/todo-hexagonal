package com.app.todo_hexagonal.application.service;

import com.app.todo_hexagonal.application.port.in.*;
import com.app.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.app.todo_hexagonal.domain.exception.TaskNotFoundException;
import com.app.todo_hexagonal.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService implements CreateTaskUseCase, GetTaskUseCase, ListTaskUseCase, CompleteTaskUseCase, ReopenTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public Task create(CreateTaskCommand command) {
        Task task = Task.create(command.title(), command.description());
        return taskRepositoryPort.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return findOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> listAll() {
        return taskRepositoryPort.listAll();
    }

    @Override
    public Task completeTask(Long id) {
        Task task = findOrThrow(id);
        task.complete();
        return taskRepositoryPort.update(task);
    }

    @Override
    public Task reopenTask(Long id) {
        Task task = findOrThrow(id);
        task.reopen();
        return taskRepositoryPort.update(task);
    }

    private Task findOrThrow(Long id) {
        return taskRepositoryPort.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}