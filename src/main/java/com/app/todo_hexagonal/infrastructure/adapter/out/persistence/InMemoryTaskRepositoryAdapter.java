package com.app.todo_hexagonal.infrastructure.adapter.out.persistence;

import com.app.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.app.todo_hexagonal.domain.model.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class InMemoryTaskRepositoryAdapter implements TaskRepositoryPort {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        Task toStore = task.getId() == null
                ? task.toBuilder().id(idSequence.getAndIncrement()).build()
                : task;
        store.put(toStore.getId(), toStore);
        return toStore;
    }

    @Override
    public List<Task> listAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Task update(Task task) {
        store.put(task.getId(), task);
        return task;
    }
}