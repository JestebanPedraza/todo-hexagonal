package com.app.todo_hexagonal.domain.model;

import com.app.todo_hexagonal.domain.exception.InvalidTaskStateException;
import com.app.todo_hexagonal.domain.exception.TaskValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void createAppliesDefaults() {
        Task task = Task.create("Título", "Descripción");

        assertNull(task.getId());
        assertEquals(TaskStatus.PENDING, task.getStatus());
        assertNotNull(task.getCreatedAt());
        assertNull(task.getCompletedAt());
    }

    @Test
    void createRejectsBlankTitle() {
        assertThrows(TaskValidationException.class, () -> Task.create("  ", "Descripción"));
        assertThrows(TaskValidationException.class, () -> Task.create(null, "Descripción"));
    }

    @Test
    void completeAndReopen() {
        Task task = Task.create("Título", "Descripción");

        task.complete();
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertNotNull(task.getCompletedAt());

        task.reopen();
        assertEquals(TaskStatus.PENDING, task.getStatus());
        assertNull(task.getCompletedAt());
    }

    @Test
    void completeTwiceFails() {
        Task task = Task.create("Título", "Descripción");
        task.complete();

        assertThrows(InvalidTaskStateException.class, task::complete);
    }

    @Test
    void reopenPendingFails() {
        Task task = Task.create("Título", "Descripción");

        assertThrows(InvalidTaskStateException.class, task::reopen);
    }

    @Test
    void equalityIsById() {
        Task a = Task.builder().id(1L).title("A").build();
        Task b = Task.builder().id(1L).title("B").build();
        Task c = Task.builder().id(2L).title("A").build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void newTasksWithoutIdAreNotEqual() {
        assertNotEquals(Task.create("A", "x"), Task.create("A", "x"));
    }
}