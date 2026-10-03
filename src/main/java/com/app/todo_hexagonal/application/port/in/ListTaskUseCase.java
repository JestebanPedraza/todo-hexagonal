package com.app.todo_hexagonal.application.port.in;

import com.app.todo_hexagonal.domain.model.Task;

import java.util.List;

public interface ListTaskUseCase {
    List<Task> listAll();
}
