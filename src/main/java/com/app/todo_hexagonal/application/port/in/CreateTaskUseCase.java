package com.app.todo_hexagonal.application.port.in;

import com.app.todo_hexagonal.domain.model.Task;

public interface CreateTaskUseCase {
    Task create(CreateTaskCommand command);
}