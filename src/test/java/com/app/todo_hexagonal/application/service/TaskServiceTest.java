package com.app.todo_hexagonal.application.service;

import com.app.todo_hexagonal.application.port.in.CreateTaskCommand;
import com.app.todo_hexagonal.domain.exception.TaskNotFoundException;
import com.app.todo_hexagonal.domain.model.Task;
import com.app.todo_hexagonal.domain.model.TaskStatus;
import com.app.todo_hexagonal.infrastructure.adapter.out.persistence.InMemoryTaskRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Sin Spring ni base de datos: el servicio solo necesita una implementación del puerto de salida.
class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(new InMemoryTaskRepositoryAdapter());
    }

    @Test
    void createAssignsIdAndDefaults() {
        Task created = service.create(new CreateTaskCommand("Título", "Descripción"));

        assertNotNull(created.getId());
        assertEquals(TaskStatus.PENDING, created.getStatus());
        assertEquals(created, service.getById(created.getId()));
    }

    @Test
    void listAllReturnsCreatedTasks() {
        service.create(new CreateTaskCommand("A", "a"));
        service.create(new CreateTaskCommand("B", "b"));

        assertEquals(2, service.listAll().size());
    }

    @Test
    void completeAndReopenTask() {
        Long id = service.create(new CreateTaskCommand("Título", "Descripción")).getId();

        assertEquals(TaskStatus.COMPLETED, service.completeTask(id).getStatus());
        assertEquals(TaskStatus.PENDING, service.reopenTask(id).getStatus());
    }

    @Test
    void unknownIdThrowsNotFound() {
        assertThrows(TaskNotFoundException.class, () -> service.getById(99L));
        assertThrows(TaskNotFoundException.class, () -> service.completeTask(99L));
        assertThrows(TaskNotFoundException.class, () -> service.reopenTask(99L));
    }
}