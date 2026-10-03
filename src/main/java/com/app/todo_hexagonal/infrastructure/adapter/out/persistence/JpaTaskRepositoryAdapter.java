package com.app.todo_hexagonal.infrastructure.adapter.out.persistence;

import com.app.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.app.todo_hexagonal.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {

    private final SpringDataTaskRepository taskRepository;
    private final TaskPersistenceMapper mapper;

    @Override
    public Task save(Task task) {
        TaskJpaEntity entity = mapper.toJpaEntity(task);
        TaskJpaEntity saved = taskRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Task> listAll() {
        return taskRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Task update(Task task) {
        TaskJpaEntity entity = mapper.toJpaEntity(task);
        TaskJpaEntity updated = taskRepository.save(entity);
        return mapper.toDomain(updated);
    }
}
