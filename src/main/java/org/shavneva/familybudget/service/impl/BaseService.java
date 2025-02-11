package org.shavneva.familybudget.service.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.BaseEntity;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@AllArgsConstructor
public class BaseService<T extends BaseEntity, ID> implements ICrudService<T> {

    private final JpaRepository<T, Integer> repository;

    public T create(T newE) {
        return repository.save(newE);
    }

    public List<T> read() {
        return repository.findAll();
    }

    public T getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity with id " + id + " not found"));
    }

    public T update(T updatedEntity) {
        int id =  updatedEntity.getId();
        return repository.save(updatedEntity);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
