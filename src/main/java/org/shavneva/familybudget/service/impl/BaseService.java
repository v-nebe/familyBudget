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

    public BaseService(JpaRepository<T, Integer> repository) {
        this.repository = repository;
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
            throw new ResourceNotFoundException("Entity with id " + id + " not found");
        }
        return repository.save(newE);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Entity with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
