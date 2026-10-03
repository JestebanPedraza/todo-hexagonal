package com.app.todo_hexagonal.domain.model;

import com.app.todo_hexagonal.domain.exception.InvalidTaskStateException;
import com.app.todo_hexagonal.domain.exception.TaskValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de dominio. El estado solo cambia a través de sus métodos de negocio.
 * <p>
 * - {@link #create(String, String)}: crea una tarea NUEVA aplicando invariantes y valores por defecto.
 * - {@link #builder()}: reconstruye una tarea YA EXISTENTE (p. ej. desde persistencia).
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Task {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public static Task create(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new TaskValidationException("El título de la tarea es obligatorio");
        }
        return Task.builder()
                .title(title)
                .description(description)
                .status(TaskStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void complete() {
        if (status == TaskStatus.COMPLETED) {
            throw new InvalidTaskStateException("La tarea ya está completada");
        }
        status = TaskStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public void reopen() {
        if (status == TaskStatus.PENDING) {
            throw new InvalidTaskStateException("La tarea ya está pendiente");
        }
        status = TaskStatus.PENDING;
        completedAt = null;
    }

    // Identidad por id: dos tareas sin id (aún no persistidas) solo son iguales si son la misma instancia.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}