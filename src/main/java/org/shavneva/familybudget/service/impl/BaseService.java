package org.shavneva.familybudget.service.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.BaseEntity;
import org.shavneva.familybudget.exception.ResourceNotFoundException;
import org.shavneva.familybudget.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public class BaseService <T extends BaseEntity, ID> implements ICrudService<T> {

    private final JpaRepository<T, Integer> repository;
    private final String entityName;

    public BaseService(JpaRepository<T, Integer> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    public T create(T newE) {
        return repository.save(newE);
    }

    public List<T> read() {
        return repository.findAll();
    }

    public T update(T newE) {
        int id = newE.getId();
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(entityName + " with id " + id + " not found");
        }
        return repository.save(newE);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(entityName + " with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
