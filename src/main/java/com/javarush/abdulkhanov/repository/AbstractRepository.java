package com.javarush.abdulkhanov.repository;

import com.javarush.abdulkhanov.entity.AbstractEntity;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractRepository<T extends AbstractEntity> implements Repository<T> {
    protected Map<Long, T> entities = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong(0);

    @Override
    public Collection<T> getAll() {
        return entities.values().stream().toList();
    }

    @Override
    public T get(Long id) {
        return entities.get(id);
    }

    @Override
    public void create(T entity) {
        entity.setId(id.incrementAndGet());
        update(entity);
    }

    @Override
    public void update(T entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void delete(T entity) {
        entities.remove(entity.getId());
    }
}
