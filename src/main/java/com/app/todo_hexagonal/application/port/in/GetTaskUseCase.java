package com.app.todo_hexagonal.application.port.in;

import com.app.todo_hexagonal.domain.model.Task;

public interface GetTaskUseCase {
    Task getById(Long id);
}
