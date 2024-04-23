package com.example.utils.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceForCRUD<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    @Transactional
    public T create(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    public T update(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    public void delete(Long id) {
        getRepository().deleteById(id);
    }
}