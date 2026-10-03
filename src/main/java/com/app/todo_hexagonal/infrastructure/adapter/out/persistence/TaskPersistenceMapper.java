package com.app.todo_hexagonal.infrastructure.adapter.out.persistence;

import com.app.todo_hexagonal.domain.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskPersistenceMapper {

    public Task toDomain(TaskJpaEntity taskEntity) {
        if (taskEntity == null) {return null;}
        return Task.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .createdAt(taskEntity.getCreatedAt())
                .completedAt(taskEntity.getCompletedAt())
                .build();
    }

    public TaskJpaEntity toJpaEntity(Task task) {
        if (task == null) {return null;}
        return TaskJpaEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .completedAt(task.getCompletedAt())
                .build();
    }
}
