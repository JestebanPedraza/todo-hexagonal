package com.app.todo_hexagonal.infrastructure.adapter.in.rest;


import com.app.todo_hexagonal.application.port.in.*;
import com.app.todo_hexagonal.domain.model.Task;
import com.app.todo_hexagonal.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.app.todo_hexagonal.infrastructure.adapter.in.rest.dto.TaskResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListTaskUseCase listTaskUseCase;
    private final GetTaskUseCase getTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final ReopenTaskUseCase reopenTaskUseCase;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task saved = createTaskUseCase.create(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponse.from(saved));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<TaskResponse> responses = listTaskUseCase.listAll()
                .stream()
                .map(TaskResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        Task task = getTaskUseCase.getById(id);
        return ResponseEntity.ok(TaskResponse.from(task));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> findByComplete(@PathVariable Long id) {
        Task completedTask = completeTaskUseCase.completeTask(id);
        return ResponseEntity.ok(TaskResponse.from(completedTask));
    }

    @PatchMapping("/{id}/reopen")
    public ResponseEntity<TaskResponse> reopenTask(@PathVariable Long id) {
        Task reopenedTask = reopenTaskUseCase.reopenTask(id);
        return ResponseEntity.ok(TaskResponse.from(reopenedTask));
    }
}
