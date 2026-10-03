package com.app.todo_hexagonal.application.port.in;

import com.app.todo_hexagonal.domain.model.Task;

public interface CompleteTaskUseCase {
    Task completeTask(Long id);
}
